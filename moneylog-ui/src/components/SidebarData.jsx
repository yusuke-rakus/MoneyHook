import React from "react";
import HomeIcon from "@mui/icons-material/Home";
import TimelineIcon from "@mui/icons-material/Timeline";
import TelegramIcon from "@mui/icons-material/Telegram";
import AccountTreeIcon from "@mui/icons-material/AccountTree";
import FormatAlignLeftIcon from "@mui/icons-material/FormatAlignLeft";
import AutoGraphIcon from "@mui/icons-material/AutoGraph";
import SettingsIcon from "@mui/icons-material/Settings";

export const SidebarData = [
  {
    title: "ホーム",
    icon: <HomeIcon />,
    link: "/home",
    heading: true,
  },
  {
    title: "タイムライン",
    icon: <TimelineIcon />,
    link: "/timeline",
    heading: false,
  },
  {
    title: "月別変動費",
    icon: <TelegramIcon />,
    link: "/variable",
    heading: false,
  },
  {
    title: "月別固定費",
    icon: <AccountTreeIcon />,
    link: "/fixed",
    heading: false,
  },
  {
    title: "したつもり貯金一覧",
    icon: <FormatAlignLeftIcon />,
    link: "/savingList",
  },
  {
    title: "したつもり貯金総額",
    icon: <AutoGraphIcon />,
    link: "/amount",
  },
  {
    title: "設定",
    icon: <SettingsIcon />,
    link: "/settings",
  },
];
