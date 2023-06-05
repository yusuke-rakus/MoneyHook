import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
/** CSS */
import "./components_CSS/TransactionListData.css";
/** 自作コンポーネント */
import SwitchBalanceButton from "./SwitchBalanceButton";
import { rootURI } from "../env/env";
/** 外部コンポーネント */
import {
  TextField,
  FormControl,
  MenuItem,
  Select,
  InputLabel,
  Checkbox,
  Tooltip,
} from "@mui/material";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";

const TransactionListData = (props) => {
  const { index, transaction, transactionList, setTransactionList } = props;
  const [cookie] = useCookies();
  const navigate = useNavigate();

  const [categoryList, setCategoryList] = useState([]);
  const [subCategoryList, setSubCategoryList] = useState([]);

  /** 日付 */
  const changeDate = (value) => {
    console.log(value);
    setTransactionList(
      transactionList.map((t, i) =>
        i === index ? { ...t, transactionDate: value } : t
      )
    );
  };

  /** カテゴリを選択 */
  const selectCategory = (e) => {
    setTransactionList(
      transactionList.map((t, i) =>
        i === index ? { ...t, categoryId: e.target.value } : t
      )
    );
    getSubCategory(e.target.value);
  };

  /** サブカテゴリを選択 */
  const selectSubCategory = (e) => {
    setTransactionList(
      transactionList.map((t, i) =>
        i === index ? { ...t, subCategoryId: e.target.value } : t
      )
    );
  };

  /** 取引名を追加 */
  const inputTransactionName = (e) => {
    setTransactionList(
      transactionList.map((t, i) =>
        i === index ? { ...t, transactionName: e.target.value } : t
      )
    );
  };

  /** 符号の変更 */
  const changeSign = () => {
    if (transaction.transactionSign === -1) {
      setTransactionList(
        transactionList.map((t, i) =>
          i === index ? { ...t, transactionSign: 1 } : t
        )
      );
    } else {
      setTransactionList(
        transactionList.map((t, i) =>
          i === index ? { ...t, transactionSign: -1 } : t
        )
      );
    }
  };

  /** 金額の変更 */
  const inputTransactionAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    if (!isNaN(tempNum)) {
      setTransactionList(
        transactionList.map((t, i) =>
          i === index ? { ...t, transactionAmount: tempNum } : t
        )
      );
    }
  };

  /** 固定費フラグ */
  const setFixed = (e) => {
    setTransactionList(
      transactionList.map((t, i) =>
        i === index ? { ...t, fixedFlg: e.target.checked } : t
      )
    );
  };

  /** サブカテゴリ取得 */
  const getSubCategory = (categoryId) => {
    fetch(`${rootURI}/subCategory/getSubCategoryList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        categoryId: categoryId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          setSubCategoryList(data.subCategoryList);
          // 成功
        } else {
          // 失敗
        }
      })
      .catch(() => setSubCategoryList([]));
  };

  /** カテゴリ取得 */
  const getCategory = () => {
    fetch(`${rootURI}/category/getCategoryList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({}),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
          setCategoryList(data.categoryList);
        } else {
          // 失敗
        }
      })
      .catch(() => setCategoryList([]));
  };

  useEffect(() => {
    !cookie.userId && navigate("/login");
    getCategory();
  }, [setCategoryList]);

  return (
    <div className="transactionListData">
      {/* 日付 */}
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker
          label={transaction.labels.transactionDate.message}
          value={transaction.transactionDate}
          maxDate={transaction.transactionDate}
          onChange={(v) => changeDate(v)}
          renderInput={(params) => (
            <TextField
              size="small"
              {...params}
              error={transaction.labels.transactionDate.status}
            />
          )}
        />
      </LocalizationProvider>

      {/* カテゴリ */}
      <FormControl
        sx={{ m: 1, width: 100 }}
        size="small"
        variant="standard"
        error={transaction.labels.categoryId.status}
      >
        <InputLabel id="demo-select-small" sx={{ fontSize: 13 }}>
          {transaction.labels.categoryId.message}
        </InputLabel>
        <Select
          labelId="demo-select-small"
          id="demo-select-small"
          sx={{ fontSize: 13 }}
          value={transaction.categoryId}
          onChange={selectCategory}
        >
          {categoryList.map((category) => {
            return (
              <MenuItem value={category.categoryId} key={category.categoryId}>
                {category.categoryName}
              </MenuItem>
            );
          })}
        </Select>
      </FormControl>

      {/* サブカテゴリ */}
      <FormControl
        sx={{ m: 1, width: 100 }}
        size="small"
        variant="standard"
        error={transaction.labels.subCategoryId.status}
      >
        <InputLabel id="demo-select-small" sx={{ fontSize: 13 }}>
          {transaction.labels.subCategoryId.message}
        </InputLabel>
        <Select
          labelId="demo-select-small"
          id="demo-select-small"
          sx={{ fontSize: 13 }}
          value={transaction.subCategoryId}
          onChange={selectSubCategory}
        >
          {subCategoryList.map((subCategory) => {
            return (
              <MenuItem
                value={subCategory.subCategoryId}
                key={subCategory.subCategoryId}
              >
                {subCategory.subCategoryName}
              </MenuItem>
            );
          })}
        </Select>
      </FormControl>

      {/* 取引名 */}
      <TextField
        id="standard-basic"
        autoComplete="off"
        label={transaction.labels.transactionName.message}
        value={transaction.transactionName}
        onChange={inputTransactionName}
        error={transaction.labels.transactionName.status}
        variant="standard"
        size="small"
      />

      {/* 収支ボタン */}
      <SwitchBalanceButton
        balance={transaction.transactionSign}
        id={index}
        changeSign={changeSign}
      />

      {/* 金額 */}
      <TextField
        id="standard-basic"
        autoComplete="off"
        label={transaction.labels.transactionAmount.message}
        value={transaction.transactionAmount.toLocaleString()}
        onChange={inputTransactionAmount}
        error={transaction.labels.transactionAmount.status}
        variant="standard"
        size="small"
        sx={{ ml: 1 }}
      />

      {/* 固定費フラグ */}
      <Tooltip title="固定費として計算">
        <Checkbox
          checked={transaction.fixedFlg}
          onChange={setFixed}
          inputProps={{ "aria-label": "controlled" }}
        />
      </Tooltip>
    </div>
  );
};

export default TransactionListData;
