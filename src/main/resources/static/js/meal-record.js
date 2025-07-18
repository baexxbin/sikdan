
let currentNutrientTargetInput = null;

function openNutrientSearchModal(button) {
    currentNutrientTargetInput = button.previousElementSibling;
    document.getElementById("nutrientSearchModal").style.display = "block";
}

function closeNutrientSearchModal() {
    document.getElementById("nutrientSearchModal").style.display = "none";
}

function searchNutrient() {
    const resultsDiv = document.getElementById("nutrientSearchResults");

    // 예시: 실제로는 fetch 사용해서 API 호출 ( -- 영양소 검색 API 연동 부분 -- )
    resultsDiv.innerHTML = `
        <button onclick="selectNutrient('CARB')">탄수화물</button>
        <button onclick="selectNutrient('PROTEIN')">단백질</button>
        <button onclick="selectNutrient('FAT')">지방</button>
        <button onclick="selectNutrient('VITAMIN')">비타민</button>
        <button onclick="selectNutrient('MINERAL')">무기질</button>
    `;
}

function selectNutrient(nutrient) {
    if (currentNutrientTargetInput) {
        currentNutrientTargetInput.value = nutrient;
    }
    closeNutrientSearchModal();
}


// 식단 등록
function addFoodItem() {
    const container = document.getElementById("foodItemsContainer");
    const item = document.createElement("div");
    item.classList.add("food-item");
    item.innerHTML = `
        <input type="text" name="foodName" placeholder="음식 이름">
        <input type="text" name="amount" placeholder="양">
        <div class="form-group nutrient-group">
            <input type="text" name="nutrientType" id="nutrientTypeInput" placeholder="영양소 입력 또는 찾기">
            <button type="button" onclick="openNutrientSearchModal(this)">영양소 찾기</button>
        </div>
        <button type="button" onclick="removeFoodItem(this)">삭제</button>
    `;
    container.appendChild(item);
}

function removeFoodItem(button) {
    button.parentElement.remove();
}


function submitMeal() {
    const form = document.getElementById("mealForm");
    const mealDate = document.getElementById("mealDate").value;
    const mealTime = form.querySelector("select[name='mealTime']").value;

    const foodItems = Array.from(document.querySelectorAll("#foodItemsContainer .food-item")).map(item => ({
        foodName: item.querySelector("input[name='foodName']").value,
        amount: item.querySelector("input[name='amount']").value,
        nutrientType: item.querySelector("input[name='nutrientType']").value,
        sourceType: "MANUAL" // 예시 값
    }));

    const requestBody = {
        memberId: 1, // 인증 미적용 상태 기준
        mealDate: mealDate,
        mealTime: mealTime,
        foodItems: foodItems
    };

    fetch("/api/meals/record", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => response.json())
        .then(data => {
            alert("등록 완료! 식단 ID: " + data);
            location.reload();
        })
        .catch(err => console.error(err));
}