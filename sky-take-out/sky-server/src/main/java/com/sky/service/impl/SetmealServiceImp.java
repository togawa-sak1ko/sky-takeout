package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImp implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 添加套餐
     * @param setmealDTO
     */
    @Transactional
    public void addMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealMapper.addMeal(setmeal);

        Long id = setmeal.getId();
        if(setmealDishes!=null && setmealDishes.size() > 0){
            setmealDishes.forEach(setmealDish -> {setmealDish.setSetmealId(id);});
            setmealDishMapper.addMealDishes(setmealDishes);
        }
    }

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> setmeals = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(setmeals.getTotal(), setmeals.getResult());
    }

    /**
     * 起售停售套餐
     * @param status
     * @param id
     * @return
     */
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    public void edit(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        setmealDishMapper.delete(setmeal.getId());
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes!=null && setmealDishes.size() > 0){
            setmealDishes.forEach(setmealDish -> {setmealDish.setSetmealId(setmeal.getId());});
            setmealDishMapper.addMealDishes(setmealDishes);
        }
    }

    /**
     * 根据id查询套餐
     * @param
     * @return
     */
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    @Transactional
    public void delete(List<Long> ids) {
        setmealMapper.delete(ids);
        setmealDishMapper.deleteByIds(ids);
    }

}
