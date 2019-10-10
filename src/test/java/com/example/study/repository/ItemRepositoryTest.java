package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.enumclass.ItemStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired ItemRepository itemRepository;

    @Test
    public void create(){

        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
//        String createdBy = "AdminServer";

        Item item = new Item();
        item.setStatus(ItemStatus.UNREGISTERED);
        item.setName("삼성 노트북");
        item.setTitle("삼성 노트북 A100");
        item.setContent("2019년형 노트북 입니다");
//        item.setPrice(900000);
        item.setBrandName("삼성");
        item.setRegisteredAt(registeredAt);
        item.setCreatedAt(createdAt);
        item.setCreatedBy("Partner01");
//        item.setPartnerId(1L);  // Long -- > Partner


//        item.setName("노트북");
//        item.setPrice(100000);
//        item.setContent("삼성 노트북");


        Item newItem = itemRepository.save(item);
        Assert.assertNotNull(newItem);

    }

    @Test
    @Transactional
    public void read(){
        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);

        Assert.assertTrue(item.isPresent());

        item.ifPresent(e -> {
            System.out.print(e);
        });

    }
}
