package com.example.common.message;

public class ValidatingMessage {

	// 共通
	/** ID未入力 */
	public static final String ID_EMPTY_ERROR = "対象を選択してください";
	/** 日付未入力 */
	public static final String TRANSACTION_DATE_EMPTY_ERROR = "日付を入力してください";
	/** 金額未入力 */
	public static final String TRANSACTION_AMOUNT_EMPTY_ERROR = "取引金額を入力してください";
	/** 取引名未入力 */
	public static final String TRANSACTION_NAME_EMPTY_ERROR = "取引名を入力してください";
	
	/** IDと名前両方未入力 */
	public static final String BOTH_OF_ID_AND_NAME_EMPTY_ERROR = "対象を選択するか、名前を入力してください";

	// 貯金目標系
	/** 貯金目標名未入力 */
	public static final String SAVING_TARGET_NAME_EMPTY_ERROR = "貯金目標名を入力してください";
	/** 貯金目標金額未入力 */
	public static final String SAVING_TARGET_AMOUNT_EMPTY_ERROR = "貯金目標金額を入力してください";
	/** 名前と目標金額両方未入力 */
	public static final String BOTH_OF_NAME_AND_TARGET_AMOUNT_EMPTY_ERROR = "名前か目標金額を入力してください";
	
	// ユーザー系
	/** メールアドレス未入力 */
	public static final String EMAIL_EMPTY_ERROR = "メールアドレスを入力してください";
	/** メールアドレス文字数 */
	public static final String EMAIL_OVER_LIMIT_ERROR = "128文字以内で入力してください";
	/** パスワード未入力 */
	public static final String PASSWORD_EMPTY_ERROR = "パスワードを入力してください";
	/** パスワード文字数 */
	public static final String PASSWORD_RANGE_ERROR = "8から32文字以内で入力してください";

	// 収支系
	/** 取引名文字数 */
	public static final String TRANSACTION_NAME_LIMIT_ERROR = "32文字以内で入力してください";
	/** カテゴリ未選択 */
	public static final String CATEGORY_NOT_SELECT_ERROR = "カテゴリを選択してください";
	/** サブカテゴリ未選択 */
	public static final String SUB_CATEGORY_NOT_SELECT_ERROR = "サブカテゴリを選択してください";
	/** サブカテゴリ文字数 */
	public static final String SUB_CATEGORY_OVER_LIMIT_ERROR = "16文字以内で入力してください";
	/**  */
	public static final String SUB_CATEGORY_NO_SELECT_AND_INPUT_ERROR = "サブカテゴリを選択するか入力してください";

}
