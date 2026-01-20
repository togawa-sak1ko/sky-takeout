package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    void addMealDishes(List<SetmealDish> setmealDishes);

    Page<SetmealDish> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void delete(Long id);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getById(Long id);

    void deleteByIds(List<Long> ids);
}
