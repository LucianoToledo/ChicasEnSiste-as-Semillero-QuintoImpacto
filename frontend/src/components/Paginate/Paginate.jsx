import ReactPaginate from "react-paginate";

const Paginate = ({pageCount, onPageChange, pageRangeDisplayed, initialPage}) => {
  return (
    <nav aria-label="navigation">
      <ReactPaginate
        breakLabel="..."
        previousLabel="<<"
        nextLabel=">>"
        renderOnZeroPageCount={null}
        pageRangeDisplayed={pageRangeDisplayed}
        pageCount={pageCount}
        onPageChange={onPageChange}
        initialPage={initialPage}
        containerClassName="flex list-style-none"
        pageClassName="page-item"
        pageLinkClassName="page-link relative block py-1.5 px-3 rounded border-0 bg-transparent outline-none transition-all duration-300 rounded text-gray-800 hover:text-gray-800 hover:bg-gray-200 focus:shadow-none"
        previousClassName="page-item"
        nextClassName="page-item"
        previousLinkClassName="page-link relative block py-1.5 px-3 rounded border-0 bg-transparent outline-none transition-all duration-300 rounded text-gray-800 hover:text-gray-800 hover:bg-gray-200 focus:shadow-none"
        nextLinkClassName="page-link relative block py-1.5 px-3 rounded border-0 bg-transparent outline-none transition-all duration-300 rounded text-gray-800 hover:text-gray-800 hover:bg-gray-200 focus:shadow-none"
        activeLinkClassName="page-link relative block py-1.5 px-3 rounded border-0 !bg-blue-600 outline-none transition-all duration-300 rounded !text-white !font-bold hover:text-white hover:bg-blue-600 shadow-md focus:shadow-md"
        breakLinkClassName="font-bold"
      />
    </nav>
  );
};
export default Paginate;
