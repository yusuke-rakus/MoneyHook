package com.example.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NameOrIDValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
// クラス名には、アノテーション名として使いたい名前をつける
public @interface AnyOneNotEmpty {

	// 入力チェック不可だった場合に、表示するエラーメッセージ
	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List {
		AnyOneNotEmpty[] value();
	}

	// チェックする値を格納
	String[] fields();

}
