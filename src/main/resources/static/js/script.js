function rotateOpenChevron() {
    let chevron = document.getElementById("chevron-open");
    if (chevron.classList.contains("rotate-chevron")) {
        chevron.classList.remove("rotate-chevron");
    } else {
        chevron.classList.add("rotate-chevron");
    }
}

function rotateClosedChevron() {
    let chevron = document.getElementById("chevron-closed");
    if (chevron.classList.contains("rotate-chevron")) {
        chevron.classList.remove("rotate-chevron");
    } else {
        chevron.classList.add("rotate-chevron");
    }
}

function checkItem(checkId) {

    function changeSaveButton() {
        document.getElementById("saveChanges").disabled = false;
        if (!document.getElementById("saveChanges").classList.contains("btn-primary")) {
            document.getElementById("saveChanges").classList.add("btn-success");
            document.getElementById("saveChanges").classList.remove("btn-secondary");
        }
    }

    let checklistRow = document.getElementById("checklist-row-" + checkId);
    let checkbox = document.getElementById("checkbox-label-" + checkId)

    if (checklistRow.classList.contains("border-dark")) {
        checklistRow.classList.remove("border-dark");
        checkbox.classList.add("btn-outline-primary");
        checkbox.classList.remove("btn-primary");
        let counter = document.getElementById("unsavedCheckCounter").value;
        if (checkbox.classList.contains("complete")) {
            changeSaveButton();
            counter++;
        } else {
            counter--;
        }
        document.getElementById("unsavedCheckCounter").value = counter;
    } else {
        checklistRow.classList.add("border-dark");
        checkbox.classList.add("btn-primary");
        checkbox.classList.remove("btn-outline-primary");
        changeSaveButton();
        let counter = document.getElementById("unsavedCheckCounter").value;
        if (checkbox.classList.contains("complete")) {
            counter--;
        } else {
            counter++;
        }
        document.getElementById("unsavedCheckCounter").value = counter;
    }
    if (document.getElementById("unsavedCheckCounter").value <= 0) {
        document.getElementById("saveChanges").disabled = true;
        if (document.getElementById("saveChanges").classList.contains("btn-primary")) {
            document.getElementById("saveChanges").classList.remove("btn-success");
            document.getElementById("saveChanges").classList.add("btn-secondary");
        }
    }
}

function updateTemplateName(templateId) {
    let templateName = document.getElementById("templateName").value;
    let btnAddItem = document.getElementById("btnAddItem");
    btnAddItem.setAttribute('href', "/templates/" + templateId + "/" + templateName + "/addItem");
}

function checkTmpItemCount() {
    let itemCount = document.querySelectorAll('.tmpItemCounter').length;
    if (itemCount > 0) {
        return true;
    } else {
        window.alert("No items in template")
        return false;
    }
}
