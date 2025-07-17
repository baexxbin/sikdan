function openNutrientSearchModal() {
    document.getElementById("nutrientSearchModal").style.display = "block";
}

function closeNutrientSearchModal() {
    document.getElementById("nutrientSearchModal").style.display = "none";
}

function searchNutrient() {
    const keyword = document.getElementById("nutrientSearchKeyword").value;
    const resultsDiv = document.getElementById("nutrientSearchResults");

    // 예시: 실제로는 fetch 사용해서 API 호출
    resultsDiv.innerHTML = `
        <button onclick="selectNutrient('CARB')">탄수화물</button>
        <button onclick="selectNutrient('PROTEIN')">단백질</button>
        <button onclick="selectNutrient('FAT')">지방</button>
        <button onclick="selectNutrient('VITAMIN')">비타민</button>
        <button onclick="selectNutrient('MINERAL')">무기질</button>
    `;
}

function selectNutrient(nutrient) {
    document.getElementById("nutrientTypeInput").value = nutrient;
    closeNutrientSearchModal();
}
