import React from "react";
import { CookiesProvider } from "react-cookie";
import { createRoot } from "react-dom/client";
import App from "./App";

const container = document.getElementById("root");
const root = createRoot(container);
root.render(
  <React.StrictMode>
    <CookiesProvider>
      <App />
    </CookiesProvider>
  </React.StrictMode>
);
