package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 添加套餐
     * @param setmealDTO
     * @return
     */
    void addMeal(SetmealDTO setmealDTO);

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 起售停售套餐
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    void edit(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐
     * @param
     * @return
     */
    SetmealVO getById(Long id);

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    void delete(List<Long> ids);
}
