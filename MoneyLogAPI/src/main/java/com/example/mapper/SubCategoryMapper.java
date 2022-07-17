package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.SubCategory;

@Mapper
public interface SubCategoryMapper {

	/** サブカテゴリを新規追加してIDをリターン */
	public Long addSubCategory(SubCategory subCategory);

}
