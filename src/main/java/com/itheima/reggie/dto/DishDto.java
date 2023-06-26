package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


/**
 * ----------------------------------多表新增------------------------------------------
 * 第一步：根据接收数据格式创建dto
 * {name: "123", price: 12300, code: "", image: "1962787a-7e69-434a-a70e-995a93b8b731.jpg",…}
 * categoryId
 * :
 * "1660633558187622401"
 * code
 * :
 * ""
 * description
 * :
 * "五"
 * flavors
 * :
 * [{name: "甜味", value: "["无糖","少糖","半糖","多糖","全糖"]", showOption: false},…]
 * image
 * :
 * "1962787a-7e69-434a-a70e-995a93b8b731.jpg"
 * name
 * :
 * "123"
 * price
 * :
 * 12300
 * status
 * :
 * 1
 */


@Data
public class DishDto extends Dish {

    //菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
