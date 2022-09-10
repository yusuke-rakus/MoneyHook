import React, { useState } from "react";
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

  const SubCategoryList = [
    { subCategoryId: 1, subCategoryName: "住宅" },
    { subCategoryId: 2, subCategoryName: "家賃" },
    { subCategoryId: 3, subCategoryName: "住宅設備" },
    { subCategoryId: 4, subCategoryName: "保険" },
  ];

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
        {SubCategoryList.map((subCategory, i) => {
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
