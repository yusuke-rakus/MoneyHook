package com.example.common.message;

public class ErrorMessage {

	// global
	/** システムエラー */
	public static final String SYSTEM_ERROR = "システムエラーが発生しました";
	/** ユーザー認証エラー */
	public static final String AUTHENTICATION_ERROR = "認証エラーが発生しました";

	// user
	/** 入力されたメールアドレスは既に登録済み */
	public static final String MAIL_ADDRESS_ALREADY_REGISTERED = "入力されたメールアドレスは既に登録済みです";
	/** メールアドレス未登録 */
	public static final String UNREGISTERED_MAIL_ADDRESS = "登録されていないメールアドレスです";
	/** パスワード不一致 */
	public static final String PASSWORD_INCORRECT = "パスワードが間違えています";
	/** ユーザー情報の取得失敗 */
	public static final String USER_INFO_GET_FAILED = "ユーザー情報の取得に失敗しました";

	// transaction
	/** 収支データの取得失敗 */
	public static final String TRANSACTION_DATA_SELECT_FAILED = "収支データの取得に失敗しました";
	/** 固定収支の取得に失敗しました */
	public static final String MONTHLY_FIXED_SPENDING_GET_FAILED = "固定収支の取得に失敗しました";
	/** タイムラインデータの取得に失敗 */
	public static final String TIMELINE_DATA_GET_FAILED = "タイムラインデータの取得に失敗しました";
	/** 変動費データの取得に失敗しました */
	public static final String MONTHLY_VARIABLE_DATA_GET_FAILED = "変動費データの取得に失敗しました";

	// monthlyTransaction
	/** 月次固定費が存在しない */
	public static final String MONTHLY_TRANSACTION_NOT_EXISTS = "月次固定費が存在しませんでした";
	/** 月次取引データの失敗 */
	public static final String DELETE_FIXED_ERROR = "月次取引データの削除に失敗しました";

	// category
	/** カテゴリデータの取得失敗 */
	public static final String CATEGORY_GET_FAILED = "カテゴリの取得に失敗しました";

	// subCategory
	/** サブカテゴリデータの取得失敗 */
	public static final String SUB_CATEGORY_GET_FAILED = "サブカテゴリの取得に失敗しました";
	/** サブカテゴリ登録済み */
	public static final String SUB_CATEGORY_ALREADY_REGISTERED = "登録されているサブカテゴリです";

	// savingTarget
	/** 貯金目標の追加失敗 */
	public static final String SAVING_TARGET_INSERT_FAILED = "貯金目標の追加に失敗しました";
	/** 貯金目標登録済み */
	public static final String SAVING_TARGET_NAME_DUPLICATED = "貯金目標名が重複しています";
	/** 貯金目標の編集失敗 */
	public static final String SAVING_TARGET_UPDATE_FAILED = "貯金目標の編集に失敗しました";
	
}
