import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
/** CSS */
import "../components_CSS/window_CSS/SubCategoryWindow.css";
/** 自作コンポーネント */
import { rootURI } from "../../env/env";
/** 外部コンポーネント */
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import CloseIcon from "@mui/icons-material/Close";
import { CircularProgress, TextField } from "@mui/material";

const SubCategoryWindow = (props) => {
  const {
    closeModalWindow,
    setSubCategoryWindowModal,
    closeCategoryWindow,
    setTransaction,
    transaction,
    error,
    setError,
  } = props;
  const [cookie] = useCookies();
  const [isLoading, setLoading] = useState(false);
  const [subCategoryList, setSubCategoryList] = useState([]);
  const [label, setLabel] = useState({
    message: "サブカテゴリを作成",
    status: false,
  });

  /** サブカテゴリを閉じる処理 */
  const closeSubCategoryWindow = () => {
    setSubCategoryWindowModal(false);
  };

  /** カテゴリのエラー表示を解除 */
  const changeError = () => {
    setError({
      ...error,
      categoryBox: {
        message: "カテゴリ",
        error: false,
      },
    });
  };

  /** チェックボックスから選択 */
  const registerSubCategory = (subCategory) => {
    // サブカテゴリ設定
    setTransaction({
      ...transaction,
      subCategoryId: subCategory.subCategoryId,
      subCategoryName: subCategory.subCategoryName,
    });

    // サブカテゴリ画面非表示
    closeSubCategoryWindow();
    // カテゴリ画面非表示
    closeCategoryWindow();
    // エラーの変更
    changeError();
  };

  /** サブカテゴリ名称の作成【Enterを2回押して登録】 */
  let enterCount = 0;
  const onEnter = (e) => {
    if (!label.status) {
      if (e.keyCode === 13) {
        enterCount++;
        if (enterCount === 2) {
          // サブカテゴリ画面非表示
          closeSubCategoryWindow();
          // カテゴリ画面非表示
          closeCategoryWindow();
          // エラーの変更
          changeError();
        }
      } else {
        enterCount = 0;
      }
    }
  };

  /** サブカテゴリテキストの変更を検知 */
  const inputTextField = (inputData) => {
    // 文字数チェック
    if (inputData.length > 16) {
      setLabel((label) => ({
        ...label,
        message: "16文字以内",
        status: inputData.length > 16,
      }));
      return;
    } else {
      setLabel((label) => ({
        ...label,
        message: "サブカテゴリを作成",
        status: inputData.length > 16,
      }));
    }

    setTransaction({
      ...transaction,
      subCategoryId: "",
      subCategoryName: inputData,
    });
  };

  /** API関連 */
  // カテゴリデータを取得
  const getInit = () => {
    setLoading(true);
    fetch(`${rootURI}/subCategory/getSubCategoryList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        categoryId: transaction.categoryId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        setLoading(false);
        setSubCategoryList(data.subCategoryList);
      })
      .catch(() => setLoading(false));
  };

  useEffect(() => {
    getInit();
  }, [setSubCategoryList]);

  return (
    <div className="sub-category-window">
      {/* 戻る / Closeボタン */}
      <span className="back-button" onClick={closeSubCategoryWindow}>
        <ChevronLeftIcon />
        戻る
      </span>
      <CloseIcon
        onClick={closeModalWindow}
        style={{ cursor: "pointer", color: "#a9a9a9" }}
        className="close-button"
      />

      {/* サブカテゴリリスト */}
      <h3 className="modal-title">サブカテゴリを選択</h3>
      <div className="sub-category-items">
        {isLoading && <CircularProgress size={30} />}
        {subCategoryList.map((subCategory, i) => {
          return (
            <div
              key={i}
              onClick={() => registerSubCategory(subCategory)}
              className="sub-category-item"
            >
              {subCategory.subCategoryName}{" "}
              <span>
                <ChevronRightIcon />
              </span>
            </div>
          );
        })}
        <TextField
          variant="standard"
          autoComplete="off"
          label={label.message}
          error={label.status}
          fullWidth={true}
          onKeyUp={onEnter}
          onChange={(e) => {
            inputTextField(e.target.value);
          }}
        />
      </div>
    </div>
  );
};
export default SubCategoryWindow;
