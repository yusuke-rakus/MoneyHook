import React from "react";
import { Link } from "react-router-dom";
import "./page_CSS/NotFound.css";
import LaunchIcon from "@mui/icons-material/Launch";

const NotFound = () => {
  return (
    <div className="notFoundPage">
      <svg
        width="300"
        height="184.41"
        viewBox="0 0 564 348"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path d="M452 88H526V347L452 318V88Z" fill="#2BBEFF" />
        <path
          d="M526.001 1.00003L526.002 88.1737L282 229L282 141.5L526.001 1.00003Z"
          fill="#2BBEFF"
        />
        <path d="M38 88.0879H112V318L38 347.088V88.0879Z" fill="#76D5FF" />
        <path
          d="M37.9996 88.0877L37.9997 0.999974L282 141.5L282 229L37.9996 88.0877Z"
          fill="#76D5FF"
        />
      </svg>
      <h1 className="message title">お探しのページは見つかりませんでした</h1>
      <p className="message subTitle">
        リンクに問題があるか、ページが削除された可能性があります
      </p>
      <Link to={"/"} className="notFoundLink">
        <LaunchIcon /> ホームへ戻る
      </Link>
    </div>
  );
};

export default NotFound;
