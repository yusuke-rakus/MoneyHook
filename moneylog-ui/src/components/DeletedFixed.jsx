import React, { useEffect, useState } from "react";
import "./components_CSS/DeleteFixed.css";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Button, CircularProgress } from "@mui/material";

const DeletedFixed = () => {
  const [monthlyTransactionList, setMonthlyTransactionList] = useState([]);
  const [isLoading, setLoading] = useState(false);

  /** API関連 */
  const rootURI = "http://localhost:8080";

  /** 戻すボタン押下処理 */
  const backMonthlyTransaction = (monthlyTransactionId) => {
    setLoading(true);
    fetch(`${rootURI}/fixed/returnTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        monthlyTransactionId: monthlyTransactionId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功処理
        } else {
          // 失敗処理
        }
      })
      .finally(() => {
        setLoading(false);
        getInit();
      });
  };

  /** 削除ボタン押下処理 */
  const deleteMonthlyTransaction = (monthlyTransactionId) => {
    setLoading(true);
    fetch(`${rootURI}/fixed/deleteFixedFromTable`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        monthlyTransactionId: monthlyTransactionId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功処理
        } else {
          // 失敗処理
        }
      })
      .finally(() => {
        setLoading(false);
        getInit();
      });
  };

  /** 計算対象外の月次データを取得 */
  const getInit = () => {
    fetch(`${rootURI}/fixed/getDeletedFixed`, {
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
        setMonthlyTransactionList(data.monthlyTransactionList);
      });
  };

  useEffect(() => {
    getInit();
  }, [setMonthlyTransactionList]);

  return (
    <>
      {monthlyTransactionList && (
        <div className="containerBox">
          <p className="settingsTitle">計算対象外の固定費</p>
          <hr className="border" />

          <TableContainer
            sx={{ marginTop: 2, marginBottom: 1 }}
            component={Paper}
          >
            <Table
              // size="small"
              sx={{ minWidth: 650 }}
              aria-label="simple table"
            >
              <TableHead>
                <TableRow>
                  <TableCell sx={{ color: "#757575" }} align="center">
                    取引名
                  </TableCell>
                  <TableCell sx={{ color: "#757575" }} align="center">
                    金額
                  </TableCell>
                  <TableCell sx={{ color: "#757575" }} align="center">
                    カテゴリ
                  </TableCell>
                  <TableCell
                    sx={{ color: "#757575" }}
                    align="center"
                    colSpan={2}
                  ></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {monthlyTransactionList.map((data, i) => (
                  <TableRow key={i} className="tableRow">
                    <TableCell align="center">
                      {data.monthlyTransactionName}
                    </TableCell>
                    <TableCell align="center">
                      {data.monthlyTransactionAmount.toLocaleString()}
                    </TableCell>
                    <TableCell align="center">{data.categoryName}</TableCell>
                    <TableCell
                      padding="checkbox"
                      align="center"
                      className="hideButton"
                    >
                      <Button
                        onClick={() =>
                          backMonthlyTransaction(data.monthlyTransactionId)
                        }
                        disabled={isLoading}
                        sx={{ marginRight: 1 }}
                        variant="contained"
                      >
                        {isLoading ? <CircularProgress size={20} /> : "戻す"}
                      </Button>
                    </TableCell>
                    <TableCell
                      padding="checkbox"
                      align="center"
                      className="hideButton"
                    >
                      <Button
                        onClick={() =>
                          deleteMonthlyTransaction(data.monthlyTransactionId)
                        }
                        disabled={isLoading}
                        sx={{ color: "#9e9e9e" }}
                      >
                        削除
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
      )}
    </>
  );
};

export default DeletedFixed;
