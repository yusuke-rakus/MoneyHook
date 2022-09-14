import React, { useEffect, useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/SubCategoryWindow.css";
/** 外部コンポーネント */
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import CloseIcon from "@mui/icons-material/Close";
import { TextField } from "@mui/material";

const SubCategoryWindow = (props) => {
  const {
    closeModalWindow,
    setSubCategoryWindowModal,
    closeCategoryWindow,
    // 以下必須
    setTransaction,
    transaction,
  } = props;

  const [subCategoryList, setSubCategoryList] = useState([]);

  /** サブカテゴリを閉じる処理 */
  const closeSubCategoryWindow = () => {
    setSubCategoryWindowModal(false);
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
  };

  /** サブカテゴリ名称の作成【Enterを2回押して登録】 */
  let enterCount = 0;
  const onEnter = (e) => {
    if (e.keyCode === 13) {
      enterCount++;
      if (enterCount === 2) {
        // サブカテゴリ画面非表示
        closeSubCategoryWindow();
        // カテゴリ画面非表示
        closeCategoryWindow();
      }
    } else {
      enterCount = 0;
    }
  };

  /** サブカテゴリテキストの変更を検知 */
  const inputTextField = (inputData) => {
    setTransaction({
      ...transaction,
      subCategoryId: "",
      subCategoryName: inputData,
    });
  };
  /** API関連 */
  const rootURI = "http://localhost:8080";

  // カテゴリデータを取得
  const getInit = () => {
    fetch(`${rootURI}/subCategory/getSubCategoryList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        categoryId: transaction.categoryId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        setSubCategoryList(data.subCategoryList);
      });
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

      {/* カテゴリリスト */}
      <h3 className="modal-title">サブカテゴリを選択</h3>
      <div className="sub-category-items">
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
          label="カテゴリを作成"
          variant="standard"
          autoComplete="off"
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
