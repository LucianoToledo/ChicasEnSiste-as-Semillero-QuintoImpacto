const Table = ({ data, column, openModal }) => {
  return (
    <table className="min-w-full">
      <thead>
        <tr>
          {column.map((item, index) => (
            <TableHeadItem key={index} item={item} />
          ))}
          <th
            className="px-6 py-3 text-sm text-left text-white border-b border-gray-200 bg-dark-steal"
            colSpan="3"
          ></th>
        </tr>
      </thead>

      <tbody className="bg-white">
        {data.map((item, index) => (
          <TableRow key={index} item={item} column={column} openModal={openModal} />
        ))}
      </tbody>
    </table>
  );
};

const TableHeadItem = ({ item }) => (
  <th className="px-6 py-3 text-xs font-medium leading-4 tracking-wider text-left text-white uppercase border-b border-gray-200 bg-dark-steal">
    {item.heading}
  </th>
);

const TableRow = ({ item, column, openModal }) => (
  <tr>
    {column.map((columnItem, index) => {
      return (
        <td key={index} className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
          <div className="flex items-center">{item[`${columnItem.value}`]}</div>
        </td>
      );
    })}
    <td className="text-sm font-medium leading-5 text-center whitespace-no-wrap border-b border-gray-200 ">
      <button href="#" className="text-indigo-600 hover:text-indigo-900" onClick={() => openModal("edit", item)}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-6 h-6"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
          />
        </svg>
      </button>
    </td>
    {/* <td className="text-sm font-medium leading-5 text-center whitespace-no-wrap border-b border-gray-200 ">
      <button href="#" className="text-gray-600 hover:text-gray-900" >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-6 h-6"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
          />
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
          />
        </svg>
      </button>
    </td> */}
    <td className="text-sm font-medium leading-5 text-center whitespace-no-wrap border-b border-gray-200 ">
      <button onClick={() => openModal("delete", item)}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-6 h-6 text-red-600 hover:text-red-800"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
          />
        </svg>
      </button>
    </td>
  </tr>
);

export default Table;
