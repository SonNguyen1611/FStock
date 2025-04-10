import { useState } from "react";

const Pagination = ({totalPage, handlePageNumber}) => {
    const [currentPage, setCurrentPage] = useState(1);
    const listNumberPage = () => {
        const pages = [];
        const maxVisiblePages = 5;

        if ( totalPage <= maxVisiblePages) {
            for (let i = 1; i <= totalPage; i++) {
                pages.push(i);
              }
        }else {
            if(currentPage <= 3){
                pages.push(1,2,3,4,5,'...', totalPage);
            }else if( currentPage >= totalPage - 2 )  {
                pages.push(1,'...',totalPage - 4, totalPage - 3, totalPage - 2, totalPage - 1, totalPage);
            }else {
                pages.push(1, '...', currentPage - 1, currentPage, currentPage + 1, '...', totalPage);     
            }
        }
        return pages
    }
    const pages = listNumberPage();
    const handleChangePageNumber = (num) => {
        handlePageNumber ('pageNumber', num )
        setCurrentPage(num)
    }
    
    return (
        <div className="shop-pagination-wrapper d-md-flex justify-content-between align-items-center">
                    <div className="basic-pagination">
                      <ul>
                        <li>
                          <a href="#">
                            <i className="fal fa-angle-left"></i>
                          </a>
                        </li>
                            {pages.map((num, index) => (
                                <li key={index}  
                                    className={currentPage === num ? 'active' : ''}
                                    >
                                  <a
                                    href="#"
                                    className={num === '...' ? 'disable' : ''}
                                    onClick={() =>
                                      handleChangePageNumber( num)
                                    }
                                  >
                                    {num}
                                  </a>
                                </li>
                            ))
                            }
                        <li>
                          <a href="#">
                            <i className="fal fa-angle-right"></i>
                          </a>
                        </li>
                      </ul>
                    </div>
                    <div className="shop__header-left">
                      <div className="show-text bottom">
                        <span>Showing 1–12 of 20 results</span>
                      </div>
                    </div>
        </div>
        
    )
}
export default Pagination;