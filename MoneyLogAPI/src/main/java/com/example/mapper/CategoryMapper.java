package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Category;

@Mapper
public interface CategoryMapper {

	/** カテゴリ一覧の取得 */
	public List<Category> getCategoryList();

}
