package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.SubCategory;
import com.example.form.GetSubCategoryListForm;

@Mapper
public interface SubCategoryMapper {

	/** サブカテゴリ一覧の取得 */
	public List<SubCategory> getSubCategoryList(GetSubCategoryListForm form);

	/** サブカテゴリを新規追加してIDをリターン */
	public Long addSubCategory(SubCategory subCategory);

}
