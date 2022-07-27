package com.example.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.example.common.message.ValidatingMessage;

@Documented
@Constraint(validatedBy = {NameOrIDValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
// クラス名には、アノテーション名として使いたい名前をつける
public @interface AnyOneNotEmpty {

  // 入力チェック不可だった場合に、表示するエラーメッセージ
  String message() default ValidatingMessage.BOTH_OF_ID_AND_NAME_EMPTY_ERROR;

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
