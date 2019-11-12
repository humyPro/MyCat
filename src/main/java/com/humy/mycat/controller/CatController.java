package com.humy.mycat.controller;

import com.humy.mycat.annotation.Logging;
import com.humy.mycat.config.AppConfig;
import com.humy.mycat.dto.out.Result;
import com.humy.mycat.entity.Cat;
import com.humy.mycat.service.CatService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 16:28
 * @Description:
 */
@RestController
@RequestMapping("/cat")
@Logging
@Api("curd cat")
public class CatController {

    private CatService catService;

    private AppConfig appConfig;

    public CatController(CatService catService, AppConfig appConfig) {
        this.catService = catService;
        this.appConfig = appConfig;
    }

    @GetMapping("{id}")
    public Result<Cat> getCatById(@PathVariable("id") Long id) {
        Cat catById = catService.getCatById(id);
        if (catById == null || catById.isDeleted()) return null;
        return Result.success(catById);
    }

    @PostMapping()
    public Result<Cat> addCat(@RequestBody Cat cat) {
        Cat cat1 = catService.CreateCat(cat);
        return Result.success(cat1);
    }

    @GetMapping("/list")
    public Result<Page<Cat>> listCat(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "0") Integer size) {
        int maxPageSize = appConfig.getMaxPageSize();
        size = size > maxPageSize || size < 1 ? maxPageSize : size;
        page = page < 0 ? 0 : page;
        Page<Cat> cats = catService.listCat(page, size);
        return Result.success(cats);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteCat(@PathVariable Long id) {
        Boolean res = catService.deleteCatById(id);
        return Result.success(res);
    }

    @PutMapping()
    public Result<Cat> updateCat(@RequestBody Cat cat) {
        if (cat == null || cat.getId() == null) {
            return Result.badRequest("");
        }
        Cat rCat = catService.updateCat(cat);
        if (rCat == null) {
            return Result.badRequest("更新失败");
        }
        return Result.success(rCat);
    }
}
