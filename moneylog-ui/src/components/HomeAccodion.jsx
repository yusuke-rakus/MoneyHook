import React from "react";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./components_CSS/HomeAccodion.css";

const HomeAccodion = (props) => {
  const { homeAccodionDataList, colorList } = props;

  return (
    <>
      {homeAccodionDataList.map((data, index) => {
        return (
          <>
            <Accordion>
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
                sx={{
                  bgcolor: colorList[index],
                }}
              >
                <Typography className="categoryGroup">
                  <span className="text">{data.categoryName}</span>
                  <span className="text">
                    {"¥" + Math.abs(data.categoryAmount).toLocaleString()}
                  </span>
                </Typography>
              </AccordionSummary>
              {data.subCategoryList.map((subCategoryData) => {
                return (
                  <>
                    <AccordionDetails>
                      <Typography className="subCategoryGroup">
                        <div className="subCategoryData">
                          <span>{subCategoryData.subCategoryName}</span>
                          <span>
                            {"¥" +
                              Math.abs(
                                subCategoryData.subCategoryAmount
                              ).toLocaleString()}
                          </span>
                        </div>
                      </Typography>
                    </AccordionDetails>
                  </>
                );
              })}
            </Accordion>
          </>
        );
      })}
    </>
  );
};
export default HomeAccodion;
