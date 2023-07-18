package com.example.mapper;

import com.example.domain.SubCategory;
import com.example.form.EditSubCategoryForm;
import com.example.form.GetSubCategoryListForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubCategoryMapper {

	/** サブカテゴリ一覧の取得 */
	public List<SubCategory> getSubCategoryList(GetSubCategoryListForm form);

	/** サブカテゴリを新規追加してIDをリターン */
	public Long addSubCategory(SubCategory subCategory);

	/** サブカテゴリ存在チェック */
	public Long checkSubCategory(SubCategory subCategory);

	/** サブカテゴリを非表示にする */
	public void disableSubCategory(EditSubCategoryForm form);

	/** サブカテゴリを表示にする */
	public void enableSubCategory(EditSubCategoryForm form);

}
