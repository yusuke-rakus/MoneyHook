import React, { useState } from "react";
/** CSS */
import "./components_CSS/SettingsFixed.css";
/** 自作コンポーネント */
import SwitchBalanceButton from "../components/SwitchBalanceButton";
/** 外部コンポーネント */
import {
  TextField,
  FormControl,
  IconButton,
  NativeSelect,
  CircularProgress,
  MenuItem,
  Select,
  InputLabel,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const SettingsFixed = (props) => {
  const {
    data,
    monthlyTransactionList,
    setMonthlyTransactionList,
    getInit,
    isLoading,
    setLoading,
    banner,
    setBanner,
  } = props;
  const [cookie, setCookie] = useCookies();
  const navigate = useNavigate();

  /** 取引名の変更 */
  const transactionChange = (e) => {
    data.monthlyTransactionName = e.target.value;
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId === data.monthlyTransactionId ? data : mt
      )
    );
  };

  /** 日付の変更 */
  const dateChange = (e) => {
    data.monthlyTransactionDate = e.target.value;
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId === data.monthlyTransactionId ? data : mt
      )
    );
  };

  /** 金額の変更 */
  const amountChange = (e) => {
    data.monthlyTransactionAmount = String(e.target.value).replace(/,/g, "");
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId === data.monthlyTransactionId ? data : mt
      )
    );
  };

  /** 符号の変更 */
  const changeSign = () => {
    if (data.monthlyTransactionSign == -1) {
      setMonthlyTransactionList(
        monthlyTransactionList.map((mt) =>
          mt.monthlyTransactionId == data.monthlyTransactionId
            ? { ...mt, monthlyTransactionSign: 1 }
            : mt
        )
      );
    } else {
      setMonthlyTransactionList(
        monthlyTransactionList.map((mt) =>
          mt.monthlyTransactionId == data.monthlyTransactionId
            ? { ...mt, monthlyTransactionSign: -1 }
            : mt
        )
      );
    }
  };

  /** API関連 */
  const rootURI = "http://localhost:8080";

  const [categoryList, setCategoryList] = useState([]);
  const [subCategoryList, setSubCategoryList] = useState([]);

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
        if (data.status == "success") {
          setCategoryList(data.categoryList);
          // 成功
        } else {
          // 失敗
        }
      });
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
        if (data.status == "success") {
          setSubCategoryList(data.subCategoryList);
          // 成功
        } else {
          // 失敗
        }
      });
  };

  /** カテゴリを選択 */
  const selectCategory = (e) => {
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId == data.monthlyTransactionId
          ? { ...mt, categoryId: e.target.value }
          : mt
      )
    );
    getSubCategory(e.target.value);
  };

  /** サブカテゴリを選択 */
  const selectSubCategory = (e) => {
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId == data.monthlyTransactionId
          ? { ...mt, subCategoryId: e.target.value }
          : mt
      )
    );
  };

  /** 削除 */
  const deleteData = () => {
    setLoading(true);
    fetch(`${rootURI}/fixed/deleteFixed`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        monthlyTransactionId: data.monthlyTransactionId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功
        } else {
          // 失敗
        }
        setBanner({
          ...banner,
          bannerMessage: data.message,
          bannerType: data.status,
          banner: true,
        });
      })
      .finally(() => {
        setLoading(false);
        getInit();
      });
  };

  useEffect(() => {
    !cookie.userId && navigate("/login");
    getCategory();
    getSubCategory(data.categoryId);
  }, [setCategoryList]);

  return (
    <div className="fixedData">
      <div className="categoryData">
        {/* カテゴリ */}
        <FormControl sx={{ m: 1, width: 100 }} size="small" variant="standard">
          <InputLabel id="demo-select-small" sx={{ fontSize: 13 }}>
            カテゴリ
          </InputLabel>
          <Select
            labelId="demo-select-small"
            id="demo-select-small"
            sx={{ fontSize: 13 }}
            value={data.categoryId}
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
        <FormControl sx={{ m: 1, width: 100 }} size="small" variant="standard">
          <InputLabel id="demo-select-small" sx={{ fontSize: 13 }}>
            サブカテゴリ
          </InputLabel>
          <Select
            labelId="demo-select-small"
            id="demo-select-small"
            sx={{ fontSize: 13 }}
            value={data.subCategoryId}
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
      </div>

      {/* 取引名 */}
      <div className="transactionNameData">
        <span>取引名</span>
        <TextField
          variant="standard"
          autoComplete="off"
          value={data.monthlyTransactionName}
          onChange={transactionChange}
          inputProps={{
            style: {
              fontSize: 13,
            },
          }}
        />
      </div>

      {/* 金額 */}
      <div className="fixedAmountData">
        <span>金額</span>
        <TextField
          variant="standard"
          autoComplete="off"
          inputProps={{
            style: {
              fontSize: 14,
              textAlign: "right",
              paddingRight: "20px",
              paddingLeft: "20px",
            },
          }}
          value={
            isNaN(data.monthlyTransactionAmount)
              ? 0
              : Math.abs(data.monthlyTransactionAmount).toLocaleString()
          }
          onChange={amountChange}
        />
      </div>

      {/* 振替日 */}
      <div className="transferDate">
        <span>振替日</span>
        <FormControl sx={{ minWidth: 60 }} size="small">
          <NativeSelect
            value={
              data.monthlyTransactionDate
                ? data.monthlyTransactionDate
                : (data.monthlyTransactionDate = 31)
            }
            onChange={dateChange}
            inputProps={{
              name: "age",
              id: "uncontrolled-native",
            }}
          >
            {[...Array(30)].map((v, i) => {
              return (
                <option key={i} value={i + 1}>
                  {i + 1}
                </option>
              );
            })}
            <option value="31">末</option>
          </NativeSelect>
        </FormControl>
        日
      </div>

      {/* 収支ボタン */}
      <div className="switchBalanceArea">
        <SwitchBalanceButton
          balance={data.monthlyTransactionSign}
          id={data.monthlyTransactionId}
          changeSign={changeSign}
        />
      </div>

      {/* 削除ボタン */}
      <div className="deleteArea">
        {isLoading ? (
          <CircularProgress size={20} />
        ) : (
          <IconButton onClick={deleteData} aria-label="delete">
            <DeleteIcon />
          </IconButton>
        )}
      </div>
    </div>
  );
};

export default SettingsFixed;
