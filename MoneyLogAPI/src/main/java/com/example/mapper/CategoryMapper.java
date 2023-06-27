package com.example.mapper;

import com.example.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

	/** カテゴリ一覧の取得 */
	public List<Category> getCategoryList();

	/** カテゴリ・サブカテゴリの取得 */
	public List<Category> getCategoryWithSubCategory(Category category);

	/** カテゴリ存在チェック */
	public boolean isCategoryExist(Category param);

}
