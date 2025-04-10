package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.response.ColorDto;
import com.example.Fstock.mapper.ColorMapper;
import com.example.Fstock.responsitory.CollorRepository;
import com.example.Fstock.service.Service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceimpl implements ColorService {
    @Autowired
    private CollorRepository collorRepository;

    @Autowired
    private ColorMapper colorMapper;
    @Override
    public List<ColorDto> getAllColors() {
        return colorMapper.toColorDtoList(collorRepository.findAll());
    }
}
