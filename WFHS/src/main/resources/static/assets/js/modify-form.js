function previewImage(event, imageId) {
    const input = event.target;
    const image = document.getElementById(imageId);
    const reader = new FileReader();
    reader.onload = function () {
        image.src = reader.result;
    };
    if (input.files && input.files[0]) {
        reader.readAsDataURL(input.files[0]);
    }
}
		