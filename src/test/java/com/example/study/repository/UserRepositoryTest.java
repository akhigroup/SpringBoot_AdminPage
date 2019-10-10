package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String account = "Test01";
        String password = "Test01";
        String status = "REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-2222";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(UserStatus.REGISTERED);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);

        User newUser = userRepository.save(user);

        Assert.assertNotNull(newUser);
//        User user = new User();
//        user.setAccount("TestUser01");
//        user.setEmail("TestUser01@gmail.com");
//        user.setPhoneNumber("010-1111-1111");
//        user.setCreatedAt(LocalDateTime.now());
//        user.setCreatedBy("admin");
//
//        User newUser = userRepository.save(user);
//        System.out.println("newUser : " +  newUser);
    }

    @Test
    @Transactional
    public void read(){

        User  user =  userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

//        User u = User.builder()
//                .account()
//                .password()
//                .email()
//                .build();

        user.getOrderGroupList().stream().forEach(e-> {
            System.out.println("---------주문묶음--------");
            System.out.println("수령인 : " + e.getRevName());
            System.out.println("수령지 : " + e.getRevAddress());
            System.out.println("총 금액 : " + e.getTotalPrice());
            System.out.println("총 수량 : " + e.getTotalQuantity());

            System.out.println("---------주문 상세--------");

            e.getOrderDetailList().forEach(orderDetail -> {
                System.out.println("파트너사 이름 : " +  orderDetail.getItem().getPartner().getName());
                System.out.println("파트너사 카테고리 :  " + orderDetail.getItem().getPartner().getCategory().getTitle());
                System.out.println("주문 상품  : " + orderDetail.getItem().getName());
                System.out.println("고객 센터  : " + orderDetail.getItem().getPartner().getCallCenter());
                System.out.println("주문 상태  : " + orderDetail.getStatus());
                System.out.println("도착 예정  : " + orderDetail.getArrivalDate());
//                System.out.println("주문 상태  : " + orderDetail.getStatus());

            });

        });

        Assert.assertNotNull(user);



//        Optional<User> user = userRepository.findByAccount("TestUser01");
//
////        System.out.println(user);
//        //있을때만 탐
//        user.ifPresent(selectUser -> {
//            selectUser.getOrderDetailList().stream().forEach(detail-> {
//                System.out.println(detail.getUser());
//                System.out.println(detail.getItem());
////                detail.getItemId();
//            });
//        });
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(e-> {
            System.out.println(e);
            e.setAccount("UpdateUser");
            e.setUpdatedAt(LocalDateTime.now());
            e.setUpdatedBy("update Method()");

            userRepository.save(e);
        });

        user.ifPresent(e-> {
            System.out.println(e);
        });
    }


    @Test
    public void delete(){
        Optional<User> user = userRepository.findById(2L);

        System.out.println(user);

//        Assert.assertTrue(user.isPresent());

        user.ifPresent(e-> {
            System.out.println(e);
            userRepository.delete(e);
            System.out.println(e);
        });

        System.out.println(user);





    }






}
