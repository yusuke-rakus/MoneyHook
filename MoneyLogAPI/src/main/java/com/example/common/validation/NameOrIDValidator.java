package com.example.common.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class NameOrIDValidator implements ConstraintValidator<AnyOneNotEmpty, Object> {

	private String[] fields;

	/**
	 * 初期化処理 ()内のクラスは下記2のアノテーションクラス
	 */
	public void initialize(AnyOneNotEmpty annotation) {
		this.fields = annotation.fields();
	}

	/**
	 * 自分でバリデーション内容を設定する
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		BeanWrapper beanWrapper = new BeanWrapperImpl(value);

		// 入力されている回数をカウントする変数を作成
		Integer idCount = 0;

		for (String string : fields) {
			// 以下で、fieldValueに、validation対象の各値（今回の場合、name1, name2）が入る
			value = beanWrapper.getPropertyValue(string);

			// 入力がある場合、入力カウントに1を足しておく
			if (value == "" || Objects.isNull(value)) {
				idCount += 1;
			}
		}

		// 入力数が2以上のときはtrueを返す
		if (idCount >= 2) {
			return false;
		}

		return true;
	}

}