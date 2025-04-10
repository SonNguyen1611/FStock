package com.example.Fstock.mapper;

import com.example.Fstock.dto.response.ReviewResponse;
import com.example.Fstock.entity.Review;
import com.example.Fstock.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "username", source = "user", qualifiedByName = "mapUserToUserName")
    ReviewResponse toReviewResponse(Review review);
    @Named("mapUserToUserName")
    default String mapUserToUserName(User user){
        return user.getUserName();
    }
}
