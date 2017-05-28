package com.supercilex.robotscouter.ui.teamlist

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.supercilex.robotscouter.R
import com.supercilex.robotscouter.data.model.Team
import com.supercilex.robotscouter.ui.KeyboardDialogBase
import com.supercilex.robotscouter.ui.scout.ScoutListFragmentBase

class NewTeamDialog : KeyboardDialogBase() {
    private val rootView: View by lazy { View.inflate(context, R.layout.dialog_new_team, null) }
    private val inputLayout: TextInputLayout by lazy { rootView.findViewById<TextInputLayout>(R.id.name) }
    override val lastEditText: EditText by lazy { inputLayout.findViewById<EditText>(R.id.team_number) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            createDialog(rootView, R.string.add_scout)

    public override fun onClick(): Boolean {
        val teamNumber: String = lastEditText.text.toString()
        if (isValid(teamNumber)) {
            (activity as TeamSelectionListener).onTeamSelected(ScoutListFragmentBase.getBundle(
                    Team.Builder(teamNumber).build(), true, null))
            return true
        } else {
            inputLayout.error = getString(R.string.invalid_team_number)
            return false
        }
    }

    private fun isValid(teamNumber: String): Boolean {
        if (TextUtils.isEmpty(teamNumber)) return false

        try {
            return teamNumber.toInt() in 0..100000
        } catch (e: NumberFormatException) {
            return false
        }
    }

    companion object {
        private val TAG = "NewTeamDialog"

        fun show(manager: FragmentManager) = NewTeamDialog().show(manager, TAG)
    }
}
