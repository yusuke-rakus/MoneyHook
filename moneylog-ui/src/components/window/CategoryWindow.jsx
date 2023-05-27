import React, { useEffect, useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/CategoryWindow.css";
/** 自作コンポーネント */
import SubCategoryWindow from "./SubCategoryWindow.jsx";
import { rootURI } from "../../env/env";
import { CSSTransition } from "react-transition-group";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import { CircularProgress } from "@mui/material";

const CategoryWindow = (props) => {
  const {
    setCategoryWindowModal,
    closeModalWindow,
    setTransaction,
    transaction,
    error,
    setError,
  } = props;

  const [SubCategoryWindowModal, setSubCategoryWindowModal] = useState(false);
  const [categoryList, setCategoryList] = useState([]);
  const [isLoading, setLoading] = useState(false);

  /** API関連 */
  // カテゴリデータを取得
  const getInit = () => {
    setLoading(true);
    fetch(`${rootURI}/category/getCategoryList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({}),
    })
      .then((res) => res.json())
      .then((data) => {
        setLoading(false);
        setCategoryList(data.categoryList);
      })
      .catch(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    getInit();
  }, [setCategoryList]);

  /** カテゴリウィンドウを閉じる */
  const closeCategoryWindow = () => {
    setCategoryWindowModal(false);
  };

  /** サブカテゴリウィンドウを開く */
  const openSubCategoryWindow = (category) => {
    if (!transaction.subCategoryName) {
      // カテゴリをセット
      setTransaction({
        ...transaction,
        categoryId: category.categoryId,
        categoryName: category.categoryName,
      });
    } else {
      // カテゴリをセットしサブカテゴリを削除
      setTransaction({
        ...transaction,
        categoryId: category.categoryId,
        categoryName: category.categoryName,
        subCategoryId: "",
        subCategoryName: "",
      });
    }

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
          {isLoading && <CircularProgress size={30} />}
          {categoryList.map((category, i) => {
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
          setTransaction={setTransaction}
          transaction={transaction}
          error={error}
          setError={setError}
        />
      </CSSTransition>
    </>
  );
};
export default CategoryWindow;
