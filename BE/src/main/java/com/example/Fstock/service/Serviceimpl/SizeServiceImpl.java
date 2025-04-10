package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.response.SizeDto;
import com.example.Fstock.mapper.SizeMapper;
import com.example.Fstock.entity.Size;
import com.example.Fstock.responsitory.SizeRepository;
import com.example.Fstock.service.Service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private SizeMapper sizeMapper;

    @Override
    public List<SizeDto> getAllSizes() {
        List<Size> sizes = sizeRepository.findAll();
        List<SizeDto> sizeDtos = sizeMapper.toSizeDtoList(sizes);
        return sizeDtos;
    }
}
