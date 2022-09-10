import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/CategoryWindow.css";
/** 自作コンポーネント */
import SubCategoryWindow from "./SubCategoryWindow.jsx";
/** 外部コンポーネント */
import { CSSTransition } from "react-transition-group";
import CloseIcon from "@mui/icons-material/Close";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

const CategoryWindow = (props) => {
  const {
    setCategoryWindowModal,
    closeModalWindow,
    // 以下必須
    setTransaction,
    transaction,
  } = props;

  const [SubCategoryWindowModal, setSubCategoryWindowModal] = useState(false);

  const CategoryList = [
    { categoryId: 1, categoryName: "食費" },
    { categoryId: 2, categoryName: "外食" },
    { categoryId: 3, categoryName: "コンビニ" },
    { categoryId: 4, categoryName: "住宅" },
  ];

  /** カテゴリウィンドウを閉じる */
  const closeCategoryWindow = () => {
    setCategoryWindowModal(false);
  };

  /** サブカテゴリウィンドウを開く */
  const openSubCategoryWindow = (category) => {
    // カテゴリをセット
    setTransaction({
      ...transaction,
      categoryId: category.categoryId,
      categoryName: category.categoryName,
    });

    // サブカテゴリウィンドウを表示
    setSubCategoryWindowModal(true);
  };

  return (
    <>
      <div className="category-window">
        {/* 戻る / Closeボタン */}
        <span className="back-button" onClick={closeCategoryWindow}>
          <ChevronLeftIcon />
          戻る
        </span>
        <CloseIcon
          onClick={closeModalWindow}
          style={{ cursor: "pointer", color: "#a9a9a9" }}
          className="close-button"
        />

        {/* カテゴリリスト */}
        <h3 className="modal-title">カテゴリを選択</h3>
        <div className="category-items">
          {CategoryList.map((category, i) => {
            return (
              <div
                key={i}
                onClick={() => openSubCategoryWindow(category)}
                className="category-item"
              >
                {category.categoryName}{" "}
                <span>
                  <ChevronRightIcon />
                </span>
              </div>
            );
          })}
        </div>
      </div>

      <CSSTransition
        in={SubCategoryWindowModal}
        timeout={300}
        unmountOnExit
        classNames="Category-show"
      >
        <SubCategoryWindow
          closeModalWindow={closeModalWindow}
          setSubCategoryWindowModal={setSubCategoryWindowModal}
          closeCategoryWindow={closeCategoryWindow}
          // 以下必須
          setTransaction={setTransaction}
          transaction={transaction}
        />
      </CSSTransition>
    </>
  );
};
export default CategoryWindow;
