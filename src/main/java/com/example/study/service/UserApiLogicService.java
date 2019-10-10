package com.example.study.service;

import com.example.study.controller.CrudController;
import com.example.study.ips.CrudInterface;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {

        //user
        User user = baseRepository.getOne(id);
        UserApiResponse userApiResponse = response2(user);

        //orderGroup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();


        List<OrderGroupApiResponse> orderGroupApiResponseList =  orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup).getData();

                    List<ItemApiResponse> itemApiResponseList =  orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item).getData())
                            .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());
        //item
        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);
        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);



    }

    // 1. request data
    // 2.  user  생성
    // 3. 생성된 데이터  -> UserApiResponse return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        //1.  request data
        UserApiRequest  userApiRequest = request.getData();

        //2. user 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser = baseRepository.save(user);

        // 3. 생성된 데이타 -> userApiReponse return

        return response(user);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        // id -> repostiroy getOne, getById
        Optional<User>  optional = baseRepository.findById(id);

        // user -> userApiResponse return

//        return optional
//                .map(user -> response(user))
//                .orElseGet(
//                        () -> Header.ERROR("데이터 없음")
//                );

        return baseRepository.findById(id)
                .map(user -> response(user))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        // 1. data
        UserApiRequest userApiRequest = request.getData();

        Optional<User> optional = baseRepository.findById(userApiRequest.getId());


        // 2. id ->  user  데이터 서치
        return optional.map(user -> {
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
        })
                .map(user -> baseRepository.save(user)) //update
                .map(user -> response(user))            // userApiResponse
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {

        Optional<User> optional = baseRepository.findById(id);

        return optional.map(user ->  {
            baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(()  -> Header.ERROR("데이터 없음"));
    }

    private Header<UserApiResponse> response(User user){
        // user --> userApiResponse

        UserApiResponse  userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unRegisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data return

        return Header.OK(userApiResponse);
    }

    private UserApiResponse response2(User user){
        UserApiResponse  userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unRegisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data return

        return userApiResponse;
    }

    public Header<List<UserApiResponse>> search(Pageable pageable) {
        Page<User> users = baseRepository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> response2(user))
                .collect(Collectors.toList());

        return Header.OK(userApiResponseList);
    }
}
