package iutinfo.lp.devmob.projetsmartspace

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import iutinfo.lp.devmob.projetsmartspace.API.GetProblem
import iutinfo.lp.devmob.projetsmartspace.Composants.ProblemView

class ProblemViewAdapterATraiter(context: Context, problems: List<GetProblem>) : ArrayAdapter<GetProblem>(context, 0, problems) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val problemView = if (convertView is ProblemView) {
            convertView
        } else {
            ProblemView(context)
        }
        problemView.populateATraiter(getItem(position))
        return problemView
    }
}