package com.example.common.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

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
		int idCount = 0;

		for (String string : fields) {
			// 以下で、fieldValueに、validation対象の各値（今回の場合、name1, name2）が入る
			value = beanWrapper.getPropertyValue(string);

			// 入力がある場合、入力カウントに1を足しておく
			if (value == "" || Objects.isNull(value)) {
				idCount += 1;
			}
		}

		// 入力数が2以上のときはtrueを返す
		return idCount < 2;
	}

}