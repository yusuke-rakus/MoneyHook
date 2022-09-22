import React, { useState } from "react";
/** CSS */
import "./components_CSS/SwitchBalanceButton.css";
/** 外部コンポーネント */
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { useEffect } from "react";

const SwitchBalanceButton = (props) => {
  // balanceの値に応じてボタンを切り替える
  // 設定していない場合は"支出"となる
  const {
    id,
    balance,
    transaction,
    setTransaction,
    monthlyTransactionList,
    setMonthlyTransactionList,
  } = props;
  let val = {};
  if (balance > 0) {
    val = {
      text: "収入",
      value: 1,
      backgroundColor: "#2b9900",
      circleTranslateX: "translateX(55px)",
      labelTraslateX: "translateX(-35px)",
    };
  } else {
    val = {
      text: "支出",
      value: -1,
      backgroundColor: "#e31826",
      translateX: "0",
    };
  }

  const [switchBalance, setSwitchBalance] = useState(val);

  const handleChange = () => {
    if (switchBalance.value === -1) {
      setSwitchBalance({
        text: "収入",
        value: 1,
        backgroundColor: "#2b9900",
        circleTranslateX: "translateX(55px)",
        labelTraslateX: "translateX(-35px)",
      });
      setTransaction({ ...transaction, transactionSign: 1 });
      // setMonthlyTransactionList(
      //   monthlyTransactionList.map((data) =>
      //     data.monthlyTransactionId == id
      //       ? { ...data, monthlyTransactionSign: 1 }
      //       : data
      //   )
      // );
    } else {
      setSwitchBalance({
        text: "支出",
        value: -1,
        backgroundColor: "#e31826",
        circleTranslateX: "translateX(0)",
        labelTraslateX: "translateX(0)",
      });
      setTransaction({ ...transaction, transactionSign: -1 });
      // setMonthlyTransactionList(
      //   monthlyTransactionList.map((data) =>
      //     data.monthlyTransactionId == id
      //       ? { ...data, monthlyTransactionSign: -1 }
      //       : data
      //   )
      // );
    }
  };

  return (
    <div className="switch">
      <input
        onChange={handleChange}
        type="checkbox"
        id={`switch-box${id}`}
        value={switchBalance.value}
        name={id && id}
      />
      <div
        onClick={() => handleChange()}
        style={{
          backgroundColor: switchBalance.backgroundColor,
          transform: switchBalance.circleTranslateX,
        }}
        className="switch-circle"
      >
        {switchBalance.value === -1 && (
          <RemoveIcon fontSize="large" className="icon" />
        )}
        {switchBalance.value === 1 && (
          <AddIcon fontSize="large" className="icon" />
        )}
      </div>
      <label
        style={{ transform: switchBalance.labelTraslateX }}
        htmlFor={`switch-box${id}`}
      >
        {switchBalance.text}
      </label>
    </div>
  );
};
console.log();
export default SwitchBalanceButton;
