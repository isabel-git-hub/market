var url = new URL(window.location.href);
var error = url.searchParams.get('error');

if (error != null) {
    document.getElementById('msg').innerText = "※ 아이디 또는 패스워드가 잘못되었습니다.";
}

function checkLoginForm() {
    const form = document.loginForm;
    const userid = document.getElementById('userid').value;
    const userpw = document.getElementById('userpw').value;

    if (userid.length < 3 || userid.length > 8) {
        window.alert('아이디는 3~8자로 입력해주세요.');
        form.userid.focus();
        return;
    }
    if (userpw.length < 4 || userpw.length > 20) {
        window.alert('패스워드는 4~20자로 입력해주세요.');
        form.userpw.focus();
        return;
    }
    form.submit();
}

function checkId() {
    var snd_data = $("#userid").val();
    $.ajax({
        type: "get",
        dataType: "text",
        async: true,
        url: "http://localhost:8020/checkid",
        data: { data: snd_data },
        success: function (data) {
            console.log("Response from server:", data);
            if (data === "true") {
                $("#id-area").html("<p>※ 사용 가능한 아이디입니다.</p>");
            } else {
                $("#id-area").html("<p>※ 사용할 수 없는 아이디입니다.</p>");
            }
        },
        error: function (xhr, textStatus, errorThrown) { // 에러 핸들링
            console.log("Error occurred:", textStatus, errorThrown);
            window.alert("에러가 발생했습니다.");
        }
    });
}

function checkJoinForm() {
    const form = document.joinForm;
    const userid = document.getElementById('userid').value;
    const userpw = document.getElementById('userpw').value;
    const username = document.getElementById('username').value;
    const birthdate = document.getElementById('birthdate').value;
    const genderM = document.getElementById('genderM').checked;
    const genderF = document.getElementById('genderF').checked;
    const telnumber = document.getElementById('telnumber').value;
    const addr = document.getElementById('addr').value;
    const email = document.getElementById('email').value;

    console.log("Form values:", {
        userid, userpw, username, birthdate, genderM, genderF, telnumber, addr, email
    });

    if(userid.length < 3 || userid.length > 8) {
        window.alert('3~8자 입력');
        form.userid.focus();
        return;
    }
    if(userpw.length < 4 || userpw.length > 20) {
        window.alert('4~20자 입력');
        form.userpw.focus();
        return;
    }
    if(username == '') {
        window.alert('이름을 입력해주세요.');
        form.username.focus();
        return;
    }
    if(birthdate == '') {
        window.alert('생년월일을 입력해주세요.');
        form.birthdate.focus();
        return;
    }
    if(!genderM && !genderF) {
        window.alert('성별을 입력해주세요.');
        return;
    }
    if(telnumber == '') {
        window.alert('전화번호를 입력해주세요.');
        form.telnumber.focus();
        return;
    }
    if(addr == '') {
        window.alert('주소를 입력해주세요.');
        form.addr.focus();
        return;
    }
    if(email == '') {
        window.alert('이메일을 입력해주세요.');
        form.addr.focus();
        return;
    }
    window.alert("회원가입이 완료되었습니다.");
    form.submit();
    //location.href='../index.html';
}

function checkMemberForm() {
    const form = document.memberForm;
    const userpw = document.getElementById('userpw').value;
    const username = document.getElementById('username').value;
    const telnumber = document.getElementById('telnumber').value;
    const addr = document.getElementById('addr').value;

    if(username == '') {
        window.alert('이름을 입력해주세요.');
        form.username.focus();
        return;
    }
    if(telnumber == '') {
        window.alert('전화번호를 입력해주세요.');
        form.telnumber.focus();
        return;
    }
    if(addr == '') {
        window.alert('주소를 입력해주세요.');
        form.addr.focus();
        return;
    }
    window.alert("회원정보가 수정되었습니다.");
    form.submit();
    //location.href='../index.html';
}

function checkFormJquery() {
    const form = $('#joinForm');
    const userid = $('#userid').val();
    const userpw = $('#userpw').val();
    const username = $('#username').val();
    const birthdate = $('#birthdate').val();
    const genderM = $('#genderM').is(':checked');
    const genderF = $('#genderF').is(':checked');
    const telnumber = $('#telnumber').val();
    const addr = $('#addr').val();

    if(userid.length < 3 || userid.length > 8) {
        window.alert('아이디는 3~8자로 입력해주세요.');
        $('#userid').focus();
        return;
    }
    if(userpw.length < 4 || userpw.length > 20) {
        window.alert('패스워드는 4~20자로 입력해주세요.');
        $('#userpw').focus();
        return;
    }
    if(username == '') {
        window.alert('이름을 입력해주세요.');
        $('#username').focus();
        return;
    }
    if(birthdate == '') {
        window.alert('생년월일을 입력해주세요.');
        $('#birthdate').focus();
        return;
    }
    if(!genderM && !genderF) {
        window.alert('성별을 입력해주세요.');
        return;
    }
    if(telnumber == '') {
        window.alert('전화번호를 입력해주세요.');
        $('#telnumber').focus();
        return;
    }
    if(addr == '') {
        window.alert('주소를 입력해주세요.');
        $('#addr').focus();
        return;
    }
    window.alert("회원가입이 완료되었습니다.");
    form.submit();
}

function findPw() {
    window.open('/findpw','비밀번호 찾기','width=420,height=400,scrollbars=no,resizable=no,history=no,status=no,menubar=no');
}

function findId() {
    window.open('/findId','아이디 찾기','width=420,height=400,scrollbars=no,resizable=no,history=no,status=no,menubar=no');
}

function userSubmit(button) {
    var userid = button.getAttribute('data-userid');
    var permitEl = button.parentElement.previousElementSibling.firstElementChild;
    var userpwEl = permitEl.parentElement.previousElementSibling.firstElementChild;
    var userpw = userpwEl.value;
    var permit = permitEl.value;
    console.log(userpw);
    console.log(permit);

    var form = document.createElement('form');
    form.action = 'editUser';
    form.method = 'post';

    var useridInput = document.createElement('input');
    useridInput.type = 'hidden';
    useridInput.name = 'userid';
    useridInput.value = userid;

    var userpwInput = document.createElement('input');
    userpwInput.type = 'hidden';
    userpwInput.name = 'userpw';
    userpwInput.value = userpw;

    var permitInput = document.createElement('input');
    permitInput.type = 'hidden';
    permitInput.name = 'permit';
    permitInput.value = permit;

    form.appendChild(useridInput);
    form.appendChild(userpwInput);
    form.appendChild(permitInput);

    document.body.appendChild(form);

    form.submit();
}

function deleteUser(button) {
    var userid = button.getAttribute('data-userid');
    location.href = "/deleteUser/" + userid;
}

// 파일 선택 버튼 클릭 시 호출되는 함수
function addFile() {
    document.getElementById('imageInput').click();
}

// 파일 선택 후 섬네일 표시 함수
function displayThumbnail() {
    const fileInput = document.getElementById('imageInput');
    const file = fileInput.files[0];
    const thumbnail = document.getElementById('imageThumbnail');

    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            thumbnail.src = e.target.result;
            thumbnail.style.display = 'block'; // 섬네일 표시
        }

        reader.readAsDataURL(file);
    } else {
        thumbnail.style.display = 'none'; // 파일이 없으면 섬네일 숨김
    }

    document.addEventListener("DOMContentLoaded", function () {
        const productContainer = document.getElementById('productContainer');

        productItems.forEach(item => {
            const img = item.querySelector('product-image');
            const imageUrl = item.getAttribute('data-image-url');

            // 이미지 URL을 설정하고 로드가 실패했을 때의 대체 처리
            img.src = imageUrl;
            img.onerror = () => {
                img.alt = '이미지 로드 실패';
                img.src = '/img/placeholder.jpg';  // 대체 이미지
            }
        })
    })
}
function updateClock() {
    const now = new Date();
    const hours = now.getHours();
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    // 12시간제 변환
    const period = hours >= 12 ? 'PM' : 'AM';
    const displayHours = hours % 12 || 12; // 12시를 12로 표시하고 0시는 12로 변환

    document.getElementById('amPm').textContent = period;
    document.getElementById('time').textContent = `${String(displayHours).padStart(2, '0')}:${minutes}:${seconds}`;
}

setInterval(updateClock, 1000); // 1초마다 시계 업데이트
updateClock(); // 페이지 로드 시 초기 시계 설정

document.getElementById('searchForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

    const keyword = document.getElementById('keyword').value;
    const resultDiv = document.getElementById('result');

    try {
        const response = await fetch('https://api.thecheat.co.kr/api/v2/fraud/search', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ keyword })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();

        // 응답 데이터 형식에 따라 결과를 처리합니다.
        if (data.results && data.results.length > 0) {
            let resultHtml = '<h3>검색 결과:</h3><ul>';
            data.results.forEach(item => {
                resultHtml += `<li>${item.someField || '정보 없음'}</li>`;
            });
            resultHtml += '</ul>';
            resultDiv.innerHTML = resultHtml;
        } else {
            resultDiv.innerHTML = '<p>검색 결과가 없습니다.</p>';
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        resultDiv.innerHTML = '<p>검색 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.</p>';
    }
});

function sendMail() {
    const form = document.forms['inquiryForm'];
    let subject;
    const to = "wonyuchung@gmail.com";
    let text;

    subject = form.name.value + "님이 문의하신 내용입니다.";
    text = `
        <table>
        <tr><td>제목</td><td>${form.title.value}</td></tr>
        <tr><td>이름</td><td>${form.name.value}</td></tr>
        <tr><td>이메일</td><td>${form.email.value}</td></tr>
        <tr><td>전화번호</td><td>${form.telno.value}</td></tr>
        <tr><td>내용</td><td>${form.content.value}</td></tr>
        </table>
    `;

    window.alert(text);

    const subjectInput = document.createElement('input');
    subjectInput.type = 'hidden';
    subjectInput.name = 'subject';
    subjectInput.value = subject;

    const toInput = document.createElement('input');
    toInput.type = 'hidden';
    toInput.name = 'to';
    toInput.value = to;

    const textInput = document.createElement('input');
    textInput.type = 'hidden';
    textInput.name = 'text';
    textInput.value = text;

    form.appendChild(subjectInput);
    form.appendChild(toInput);
    form.appendChild(textInput);

    form.submit();
}

document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.querySelector('.search-form');

    searchForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const query = document.querySelector('.search-input').value;
        const page = 1; // 기본 페이지 번호

        fetch(`/search?query=${encodeURIComponent(query)}&page=${page}`)
            .then(response => response.json())
            .then(data => {
                document.querySelector('.post-table tbody').innerHTML = data.html;
                document.querySelector('.pagination').innerHTML = data.pagination;
            })
            .catch(error => console.error('Error:', error));
    });
});


