import { RouterProvider } from "react-router-dom";
import routers from "./routes/index.jsx"; // Cập nhật phần mở rộng

const App = () => {
  return (
    <>
      <RouterProvider router={routers} />
    </>
  );
};

export default App;
