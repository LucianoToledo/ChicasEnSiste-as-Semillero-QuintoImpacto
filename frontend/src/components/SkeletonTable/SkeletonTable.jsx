const SkeletonTable = () => {
  return (
    <div className="animate-pulse">
      <table className="min-w-full">
        <thead className="w-full">
          <tr className="w-full">
            <th
              className="px-6 py-3 text-sm text-left text-white border-b border-gray-200 bg-dark-steal w-full"
              colSpan="3"
            >
              <div className="h-2 bg-slate-200 rounded"></div>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200 w-full">
              <div className="h-2 bg-slate-200 rounded"></div>
            </td>
          </tr>
          <tr>
            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200 w-full">
              <div className="h-2 bg-slate-200 rounded"></div>
            </td>
          </tr>
          <tr>
            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200 w-full">
              <div className="h-2 bg-slate-200 rounded"></div>
            </td>
          </tr>
          <tr>
            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200 w-full">
              <div className="h-2 bg-slate-200 rounded"></div>
            </td>
          </tr>
          <tr>
            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200 w-full">
              <div className="h-2 bg-slate-200 rounded"></div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};
export default SkeletonTable;
