package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.ips.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {

    @GetMapping("")
    public Header<List<UserApiResponse>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 15) Pageable pageable) {
        log.info("{}", pageable);
        return userApiLogicService.search(pageable);
    }

    @GetMapping("/{id}/orderInfo")
    public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id) {
        return userApiLogicService.orderInfo(id);
    }


    @Autowired
    private UserApiLogicService userApiLogicService;
//
//    @Override
//    @PostMapping("")  // /api/user
//    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
//        log.info("{} , {}",request , "request  정보");  // requset.toString, "정보"
//        return userApiLogicService.create(request);
//    }
//
//    @Override
//    @GetMapping("{id}")  // api/user/{id}
//    public Header read(@PathVariable Long id) {
//        log.info("read id : {} ", id);
//        return userApiLogicService.read(id);
//    }
//
//    @Override
//    @PutMapping("")
//    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {
//        return userApiLogicService.update(request);
//    }
//
//
//    @Override
//    @DeleteMapping("{id}")  //  /api/user/id
//    public Header delete(@PathVariable Long id) {
//        log.info("delete  id {} ", id);
//        return userApiLogicService.delete(id);
//    }
}
