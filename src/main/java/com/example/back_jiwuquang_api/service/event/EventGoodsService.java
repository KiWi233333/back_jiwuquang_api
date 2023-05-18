package com.example.back_jiwuquang_api.service.event;

import com.example.back_jiwuquang_api.repository.event.EventGoodsMapper;
import com.example.back_jiwuquang_api.repository.event.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventGoodsService {

    @Autowired
    EventGoodsMapper eventGoodsMapper;


}
