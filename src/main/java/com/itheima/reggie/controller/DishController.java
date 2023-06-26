package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.impl.SetmealDishServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;



    /**
     * -------------------------------多表新增--------------------------------------
     * 第三步：书写controller，返回结构
     *
     * Request URL:
     * http://localhost:8080/dish
     * Request Method:
     * POST
     *
      */

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功");
    }

    /*
    ---------------------------------------------------------------------------------
     */

//    @PostMapping
//    public R<String> save(@RequestBody DishDto dishDto){
//        log.info(dishDto.toString());
//
//        dishService.saveWithFlavor(dishDto);
//
//        return R.success("新增菜品成功");
//    }


    /**
     * -------------------------------多表新增--------------------------------------
     */











    /**
     * -------------------------------多表条件分页查询--------------------------------------
     * Request URL:
     * http://localhost:8080/dish/page?page=1&pageSize=10&name=%E5%93%88
     * Request Method:
     * GET
     *
     */

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }



    /*
    ---------------------------------------------------------------------------------
     */

//    @GetMapping("/page")
//    public R<Page> page(int page,int pageSize,String name){
//
//        //构造分页构造器对象
//        Page<Dish> pageInfo = new Page<>(page,pageSize);
//        Page<DishDto> dishDtoPage = new Page<>();
//
//        //条件构造器
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        //添加过滤条件
//        queryWrapper.like(name != null,Dish::getName,name);
//        //添加排序条件
//        queryWrapper.orderByDesc(Dish::getUpdateTime);
//
//        //执行分页查询
//        dishService.page(pageInfo,queryWrapper);
//
//        //对象拷贝
//        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
//
//        List<Dish> records = pageInfo.getRecords();
//
//        List<DishDto> list = records.stream().map((item) -> {
//            DishDto dishDto = new DishDto();
//
//            BeanUtils.copyProperties(item,dishDto);
//
//            Long categoryId = item.getCategoryId();//分类id
//            //根据id查询分类对象
//            Category category = categoryService.getById(categoryId);
//
//            if(category != null){
//                String categoryName = category.getName();
//                dishDto.setCategoryName(categoryName);
//            }
//            return dishDto;
//        }).collect(Collectors.toList());
//
//        dishDtoPage.setRecords(list);
//
//        return R.success(dishDtoPage);
//    }


    /**
     * -------------------------------多表修改-------------------------------------
     *
     * 第二步：controller
     *
     *
     *
     * Request URL:
     * http://localhost:8080/dish/1666734158906249217
     * Request Method:
     * GET
     */




    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     *
     * Request URL:
     * http://localhost:8080/dish/1666734158906249217
     * Request Method:
     * GET
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     *
     * Request URL:
     * http://localhost:8080/dish
     * Request Method:
     * PUT
     *
     *
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        return R.success("修改菜品成功");
    }


        /*
    ---------------------------------------------------------------------------------
     */


//    /**
//     * 根据id查询菜品信息和对应的口味信息
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    public R<DishDto> get(@PathVariable Long id){
//
//        DishDto dishDto = dishService.getByIdWithFlavor(id);
//
//        return R.success(dishDto);
//    }
//
//    /**
//     * 修改菜品
//     * @param dishDto
//     * @return
//     */
//    @PutMapping
//    public R<String> update(@RequestBody DishDto dishDto){
//        log.info(dishDto.toString());
//
//        dishService.updateWithFlavor(dishDto);
//
//        return R.success("修改菜品成功");
//    }

    /**
     * -------------------------------多表修改-------------------------------------
     */







    /**
     * 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    /*@GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        return R.success(list);
    }*/

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }




    /**
     * 根据id修改菜品的状态status(停售和起售)
     *
     *0停售，1起售。
     * @param status
     * @param
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatusById(@PathVariable Integer status, Long[] ids) {
        // 增加日志验证是否接收到前端参数。
        log.info("根据id修改菜品的状态:{},id为：{}", status, ids);
        // 通过id查询数据库。修改id为ids数组中的数据的菜品状态status为前端页面提交的status。
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            //根据id得到每个dish菜品。
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改菜品状态成功");
    }





    /**
     * -------------------------------多表批量删除-------------------------------------
     */



    /**
     * 根据id删除一个或批量删除菜品。
     *
     * @param ids 待删除的菜品id。
     * @return
     *
     *
     * Request URL:
     * http://localhost:8080/dish?ids=1397849739276890114,1397850140982161409
     * Request Method:
     * DELETE
     *
     */
    @DeleteMapping
    public R<String> delete(Long[] ids) {
        // 增加日志验证是否接收到前端参数。
        log.info("根据id删除一个菜品:{}", ids);
        // 根据ids中的id值删除所有的菜品。
        List<Long> idList = Arrays.asList(ids);
        // 还要删除菜品所对应的口味记录
        // 根据id删除所对应的口味对象。
        for (int i = 0; i < ids.length; i++) {
            // 得到每个dish对象
            Long id = ids[i];
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, id);
            // 删除菜品对应口味记录。
            //      DishFlavor dishFlavor = dishFlavorService.getById(queryWrapper);
            dishFlavorService.remove(queryWrapper);
            //      System.out.println("删除对应口味是否成功："+b);
        }
        // 执行删除菜品。
        if (!dishService.removeByIds(idList)) {
            return R.success("删除菜品失败");
        }

        return R.success("删除菜品成功");
    }


        /*
    ---------------------------------------------------------------------------------
     */


//    /**
//     * 根据id删除一个或批量删除菜品。
//     *
//     * @param ids 待删除的菜品id。
//     * @return
//     */
//    @DeleteMapping
//    public R<String> delete(Long[] ids) {
//        // 增加日志验证是否接收到前端参数。
//        log.info("根据id删除一个菜品:{}", ids);
//        // 根据ids中的id值删除所有的菜品。
//        List<Long> idList = Arrays.asList(ids);
//        // 还要删除菜品所对应的口味记录
//        // 根据id删除所对应的口味对象。
//        for (int i = 0; i < ids.length; i++) {
//            // 得到每个dish对象
//            Long id = ids[i];
//            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(DishFlavor::getDishId, id);
//            // 删除菜品对应口味记录。
//            //      DishFlavor dishFlavor = dishFlavorService.getById(queryWrapper);
//            dishFlavorService.remove(queryWrapper);
//            //      System.out.println("删除对应口味是否成功："+b);
//        }
//        // 执行删除菜品。
//        if (!dishService.removeByIds(idList)) {
//            return R.success("删除菜品失败");
//        }
//
//        return R.success("删除菜品成功");
//    }






    /**
     * -------------------------------多表批量删除-------------------------------------
     */


    /**
     * 点击套餐图片查看套餐具体内容
     * 前端主要要展示的信息是:套餐中菜品的基本信息，图片，菜品描述，以及菜品的份数
     * @param SetmealId
     * @return
     */
    @GetMapping("/dish/{id}")
    public R<List<DishDto>> dish(@PathVariable("id") Long SetmealId) {

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, SetmealId);

        // 获得数据
         SetmealDishService setmealDishService = new SetmealDishServiceImpl();
        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        List<DishDto> dishDtos = list.stream().map((setmealDish) -> {
            DishDto dishDto = new DishDto();

            // 基本信息拷贝
            BeanUtils.copyProperties(setmealDish, dishDto);

            // 设置其他信息
            Long dishId = setmealDish.getDishId();
            Dish dish = dishService.getById(dishId);
            BeanUtils.copyProperties(dish, dishDto);

            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtos);
    }

}
