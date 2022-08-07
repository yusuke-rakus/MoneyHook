import React, { useState } from "react";
import "./components_CSS/CategoryWindow.css";
import { CSSTransition } from "react-transition-group";
import CloseIcon from "@mui/icons-material/Close";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import SubCategoryWindow from "./SubCategoryWindow.jsx";

const CategoryWindow = (props) => {
  const {
    CategoryWindowModal,
    setCategoryWindowModal,
    closeModalWindow,
    setCategory,
    setSubCategory,
  } = props;

  const [SubCategoryWindowModal, setSubCategoryWindowModal] = useState(false);

  const CategoryList = [
    { name: "食費", value: 1 },
    { name: "外食", value: 2 },
    { name: "コンビニ", value: 3 },
    { name: "住宅", value: 4 },
  ];

  const closeCategoryWindow = () => {
    setCategoryWindowModal(false);
  };

  const openSubCategoryWindow = (category) => {
    setCategory(category);
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
          {CategoryList.map((category) => {
            return (
              <div
                onClick={() => openSubCategoryWindow(category.name)}
                className="category-item"
                key={category}
              >
                {category.name}{" "}
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
          SubCategoryWindowModal={SubCategoryWindowModal}
          setSubCategoryWindowModal={setSubCategoryWindowModal}
          closeCategoryWindow={closeCategoryWindow}
          setSubCategory={setSubCategory}
        />
      </CSSTransition>
    </>
  );
};
export default CategoryWindow;
