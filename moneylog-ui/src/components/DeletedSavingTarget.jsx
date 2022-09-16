import React, { useState } from "react";

const DeletedSavingTarget = () => {
  const [monthlyTransactionList, setMonthlyTransactionList] = useState([
    {
      monthlyTransactionId: "1",
      monthlyTransactionName: "sample",
      monthlyTransactionAmount: "100",
      monthlyTransactionDate: "25",
      categoryName: "cate",
      subCategoryName: "subc",
    },
    {
      monthlyTransactionId: "2",
      monthlyTransactionName: "sample",
      monthlyTransactionAmount: "100",
      monthlyTransactionDate: "25",
      categoryName: "cate",
      subCategoryName: "subc",
    },
  ]);

  /** API関連 */
  const rootURI = "http://localhost:8080";

  // ユーザー情報の取得
  const getInit = () => {
    fetch(`${rootURI}/fixed/getDeleteFixed`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setMonthlyTransactionList(data.monthlyTransactionList);
        }
      });
  };

  //   useEffect(() => {
  //     getInit();
  //   }, [setMonthlyTransactionList]);

  return (
    <div className="containerBox">
      <p className="settingsTitle">削除済み目標</p>
      <hr className="border" />
    </div>
  );
};

export default DeletedSavingTarget;
