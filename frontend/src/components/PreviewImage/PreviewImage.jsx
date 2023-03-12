import { useState } from "react";

const PreviewImage = ({ photo }) => {
  const [preview, setPreview] = useState("");

  const reader = new FileReader();
  reader.readAsDataURL(photo);
  reader.onload = () => setPreview(reader.result);
  return (
    <>
      {preview ? <img src={preview} alt="preview" width="200px" /> : "Loading..."}
    </>
  );
};
export default PreviewImage;
