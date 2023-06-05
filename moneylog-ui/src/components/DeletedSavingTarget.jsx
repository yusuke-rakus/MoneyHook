import React, { useEffect, useState } from "react";
import "./components_CSS/DeleteSavingTarget.css";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Button, CircularProgress } from "@mui/material";
import { useCookies } from "react-cookie";
import { rootURI } from "../env/env";
import { SettingsFetchError } from "./FetchError";

const DeletedSavingTarget = (props) => {
  const { banner, setBanner } = props;
  const [cookie] = useCookies();
  const [savingTarget, setSavingTarget] = useState([]);
  const [isLoading, setLoading] = useState(false);

  /** API関連 */
  /** 戻すボタン押下処理 */
  const backSavingTarget = (savingTargetId) => {
    setBanner({
      ...banner,
      banner: false,
    });
    setLoading(true);
    fetch(`${rootURI}/savingTarget/returnSavingTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        savingTargetId: savingTargetId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功処理
        } else {
          // 失敗処理
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
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  /** 削除ボタン押下処理 */
  const deleteSavingTarget = (savingTargetId) => {
    setBanner({
      ...banner,
      banner: false,
    });
    console.log(savingTarget);
    setLoading(true);
    fetch(`${rootURI}/savingTarget/deleteSavingTargetFromTable`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        savingTargetId: savingTargetId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功処理
        } else {
          // 失敗処理
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
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  const getInit = () => {
    fetch(`${rootURI}/savingTarget/getDeletedSavingTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          if (data.savingTarget === 0) {
            setSavingTarget(null);
          } else {
            setSavingTarget(data.savingTarget);
          }
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  useEffect(() => {
    getInit();
  }, [setSavingTarget]);

  return (
    <>
      {savingTarget && (
        <div className="containerBox">
          <p className="settingsTitle">削除済み目標</p>
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
                    貯金目標
                  </TableCell>
                  <TableCell sx={{ color: "#757575" }} align="center">
                    目標金額
                  </TableCell>
                  <TableCell
                    sx={{ color: "#757575" }}
                    align="center"
                    colSpan={2}
                  ></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {savingTarget.map((data, i) => (
                  <TableRow key={i} className="tableRow">
                    <TableCell align="center">
                      {data.savingTargetName}
                    </TableCell>
                    <TableCell align="center">
                      {data.targetAmount.toLocaleString()}
                    </TableCell>
                    <TableCell
                      padding="checkbox"
                      align="center"
                      className="hideButton"
                    >
                      <Button
                        onClick={() => backSavingTarget(data.savingTargetId)}
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
                        onClick={() => deleteSavingTarget(data.savingTargetId)}
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

export default DeletedSavingTarget;
