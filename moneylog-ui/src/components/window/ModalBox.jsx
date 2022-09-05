import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/ModalBox.css";
import "../components_CSS/window_CSS/CategoryWindow.css";
/** 自作コンポーネント */
import CategoryWindow from "./CategoryWindow";
import SwitchBalanceButton from "../SwitchBalanceButton";
/** 外部コンポーネント */
import {
  Button,
  FormControl,
  Select,
  MenuItem,
  FormGroup,
  FormControlLabel,
  Checkbox,
  TextField,
} from "@mui/material";
import { CSSTransition } from "react-transition-group";
import CloseIcon from "@mui/icons-material/Close";

const ModalBox = (props) => {
  const [date, setDate] = useState({ year: "", month: "", day: "" });
  const setYear = (e) => setDate({ ...date, year: e.target.value });
  const setMonth = (e) => setDate({ ...date, month: e.target.value });
  const setDay = (e) => setDate({ ...date, day: e.target.value });

  /** 以下必須 */
  const { transaction, setTransaction, openWindow } = props;

  /** ウィンドウを閉じる */
  const closeModalWindow = () => {
    setTransaction({});
    openWindow(false);
  };

  /** 登録ボタン押下 */
  const registerTransaction = () => {
    closeModalWindow();
  };

  /** 金額入力 */
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    setTransaction({
      ...transaction,
      transactionAmount: tempNum,
      // transactionAmount: Number(tempNum).toLocaleString(),
    });
  };

  const [CategoryWindowModal, setCategoryWindowModal] = useState(false);
  const openCategoryWindow = () => {
    setCategoryWindowModal(true);
  };

  /** 取引名入力 */
  const changeTransactionName = (e) => {
    setTransaction({ ...transaction, transactionName: e.target.value });
  };

  return (
    <>
      <div className="modal-window">
        <CloseIcon
          onClick={closeModalWindow}
          style={{ cursor: "pointer", color: "#a9a9a9" }}
          className="close-button"
        />
        <h3 className="modal-title">支出または収入の入力</h3>

        {/* 日付入力 */}
        <div className="input-date-box">
          <span className="input-span">日付</span>
          <div className="date-group">
            <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={date.year}
                onChange={setYear}
                defaultValue={2020}
              >
                <MenuItem value={2020}>2020</MenuItem>
                <MenuItem value={2021}>2021</MenuItem>
              </Select>
            </FormControl>
            <span>年</span>
            <FormControl sx={{ m: 1, minWidth: 60 }} size="small">
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={date.month}
                onChange={setMonth}
              >
                <MenuItem value={6}>6</MenuItem>
                <MenuItem value={7}>7</MenuItem>
              </Select>
            </FormControl>
            <span>月</span>
            <FormControl sx={{ m: 1, minWidth: 60 }} size="small">
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={date.day}
                onChange={setDay}
              >
                <MenuItem value={25}>25</MenuItem>
                <MenuItem value={26}>26</MenuItem>
              </Select>
            </FormControl>
            <span>日</span>
          </div>
        </div>

        {/* 金額入力 */}
        <div className="input-amount-box">
          <SwitchBalanceButton
            balance={
              isNaN(transaction.transactionAmount)
                ? 0
                : transaction.transactionAmount
            }
          />
          <div className="amount-group">
            <span className="input-span">金額</span>
            <div className="input-amount">
              <TextField
                id="standard-basic"
                variant="standard"
                fullWidth={true}
                inputProps={{
                  style: {
                    textAlign: "right",
                    paddingRight: "20px",
                    paddingLeft: "20px",
                    fontSize: "20px",
                    color: "#424242",
                  },
                }}
                value={
                  isNaN(transaction.transactionAmount)
                    ? 0
                    : Math.abs(transaction.transactionAmount).toLocaleString()
                }
                onChange={changeAmount}
              />
              <span className="input-span">円</span>
            </div>
          </div>
        </div>

        {/* 取引名入力 */}
        <div className="input-transaction-box">
          <span className="input-span">取引名</span>
          <div className="input-transaction">
            <TextField
              id="standard-basic"
              variant="standard"
              fullWidth={true}
              inputProps={{
                style: {
                  paddingRight: "20px",
                  paddingLeft: "20px",
                  fontSize: "20px",
                  color: "#424242",
                },
              }}
              value={transaction && transaction.transactionName}
              onChange={changeTransactionName}
            />
          </div>
        </div>

        {/* カテゴリ入力 */}
        <div className="input-category-box">
          <span className="input-span category-span">カテゴリ</span>
          <div onClick={openCategoryWindow} className="category-box">
            <span>
              {!transaction.subCategoryName
                ? ""
                : transaction.categoryName +
                  " / " +
                  transaction.subCategoryName}
            </span>
            <span>&gt;</span>
          </div>
        </div>

        {/* 固定費チェックボックス */}
        <FormGroup>
          <FormControlLabel
            className={"fixed-check-box"}
            control={<Checkbox color="default" />}
            label="固定費として計算する"
          />
        </FormGroup>

        {/* 登録ボタン */}
        <Button onClick={registerTransaction} variant="contained">
          登録
        </Button>
      </div>

      <CSSTransition
        in={CategoryWindowModal}
        timeout={300}
        unmountOnExit
        classNames="Category-show"
      >
        <CategoryWindow
          setCategoryWindowModal={setCategoryWindowModal}
          closeModalWindow={closeModalWindow}
          // 以下必須
          setTransaction={setTransaction}
          transaction={transaction}
        />
      </CSSTransition>
    </>
  );
};

export default ModalBox;
