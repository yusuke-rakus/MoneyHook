package com.example.mapper;

import com.example.domain.Category;
import com.example.form.CategoryWithSubCategoryListForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

	/** カテゴリ一覧の取得 */
	public List<Category> getCategoryList();

	/** カテゴリ・サブカテゴリの取得 */
	public List<Category> getCategoryWithSubCategory(Category category);

	/** カテゴリ存在チェック */
	public boolean isCategoryExist(Category param);

	/** 対象のカテゴリ取得 */
	public List<Category> getCategoryWithSubCategoryByIds(@Param("ids") List<Long> ids, @Param("userNo") Long userNo);

	/** カテゴリ・サブカテゴリ一覧取得 */
	public List<Category> getCategoryWithSubCategoryList(CategoryWithSubCategoryListForm form);

}
