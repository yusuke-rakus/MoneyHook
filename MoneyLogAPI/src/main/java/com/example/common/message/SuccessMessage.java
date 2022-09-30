package com.example.common.message;

public class SuccessMessage {

	// user
	/** テーマカラーを変更 */
	public static final String USER_THEME_COLOR_CHANGED = "テーマカラーを変更しました";
	/** パスワードを変更 */
	public static final String USER_PASSWORD_CHANGED = "パスワードを変更しました";
	/** メールアドレスを変更 */
	public static final String USER_EMAIL_CHANGED = "メールアドレスを変更しました";
	/** ユーザー登録完了 */
	public static final String CREATE_USER_REGISTERD_SUCCESS = "ユーザー登録が完了しました。ログインして利用を開始しましょう";

	// savingTarget
	/** 貯金目標一覧の取得成功 */
	public static final String SAVING_TARGET_LIST_GET_SUCCESSED = "貯金目標一覧の取得が完了しました。";
	/** 削除済み貯金目標一覧の取得成功 */
	public static final String DELETED_SAVING_TARGET_LIST_GET_SUCCESSED = "削除済み貯金目標一覧の取得が完了しました。";
	/** 貯金目標の追加成功 */
	public static final String SAVING_TARGET_INSERT_SUCCESSED = "貯金目標の追加が完了しました。";
	/** 貯金目標の編集成功 */
	public static final String SAVING_TARGET_EDIT_SUCCESSED = "貯金目標の編集が完了しました。";
	/** 貯金目標の削除成功 */
	public static final String SAVING_TARGET_DELETE_SUCCESSED = "貯金目標の削除が完了しました。";
	/** 貯金目標を戻す */
	public static final String SAVING_TARGET_RETURN_SUCCESSED = "貯金目標を有効にしました";
	/** 貯金目標ごとの貯金金額一覧の取得成功 */
	public static final String SAVING_TARGET_AMOUNT_LIST_GET_SUCCESSED = "貯金目標ごとの貯金金額一覧の取得が完了しました。";

	// saving
	/** 貯金一覧の取得成功 */
	public static final String SAVING_LIST_GET_SUCCESSED = "貯金一覧の取得が完了しました。";
	/** 貯金詳細の取得成功 */
	public static final String SAVING_DATA_GET_SUCCESSED = "貯金詳細の取得が完了しました。";
	/** 貯金の編集成功 */
	public static final String SAVING_EDIT_SUCCESSED = "貯金の編集が完了しました。";
	/** 貯金の追加成功 */
	public static final String SAVING_INSERT_SUCCESSED = "貯金の追加が完了しました。";
	/** 貯金の削除成功 */
	public static final String SAVING_DATA_DELETE_SUCCESSED = "貯金の削除が完了しました。";
	/** 貯金の振り分け成功 */
	public static final String SAVING_ALLOT_SUCCESSED = "貯金の振り分けが完了しました。";
	/** 貯金総額の取得成功 */
	public static final String SAVING_TOTAL_DATA_GET_SUCCESSED = "貯金総額の取得が完了しました。";

	// transaction
	/** 取引の追加成功 */
	public static final String TRANSACTION_INSERT_SUCCESSED = "データの追加が完了しました";
	/** 取引の編集成功 */
	public static final String TRANSACTION_EDIT_SUCCESSED = "データの編集が完了しました";
	/** 取引の削除成功 */
	public static final String TRANSACTION_DELETE_SUCCESSED = "データの削除が完了しました";

	// monthlyTransaction
	/** 編集の成功 */
	public static final String MONTHLY_TRANSACTION_EDIT_SUCCESSED = "月次データの編集が完了しました";
	/** 削除の成功 */
	public static final String MONTHLY_TRANSACTION_DELETE_SUCCESSED = "計算対象外に設定しました";
	/** 計算対象に戻す */
	public static final String MONTHLY_TRANSACTION_BACK_SUCCESSED = "計算対象に戻しました";
	/** 完全に削除 */
	public static final String MONTHLY_TRANSACTION_DELETE_FROM_TABLE_SUCCESSED = "データの削除が完了しました";

}
